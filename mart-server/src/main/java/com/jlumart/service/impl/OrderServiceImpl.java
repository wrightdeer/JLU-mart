package com.jlumart.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jlumart.constant.ShoppingOrderStatusConstant;
import com.jlumart.context.BaseContext;
import com.jlumart.dto.OrderCancelDTO;
import com.jlumart.dto.OrderDTO;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.dto.OrderPayDTO;
import com.jlumart.entity.*;
import com.jlumart.exception.IllegalOperationException;
import com.jlumart.mapper.*;
import com.jlumart.result.PageResult;
import com.jlumart.service.AddressBookService;
import com.jlumart.service.OrderService;
import com.jlumart.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public OrderPayVO createOrder(OrderDTO orderDTO) {
        Long userId = BaseContext.getCurrentId();
        Long addressBookId = orderDTO.getAddressBookId();
        if (addressBookId == null) {
            throw new IllegalOperationException("地址簿不能为空");
        }
        AddressBook addressBook = addressBookMapper.getAddressBook(addressBookId, userId);
        DeliveryRangeVO deliveryRange = addressBookService.getDeliveryRange(addressBookId);
        if (deliveryRange == null || addressBook == null) {
            throw new IllegalOperationException("地址簿不存在");
        }
        if (deliveryRange.getIsInRange() == 0) {
            throw new IllegalOperationException("不在配送范围");
        }
        String itemsJson;
        Double amount = deliveryRange.getDeliveryFee();
        if (orderDTO.getOrderType() == 0) {
            // 单件商品订单
            ProductVO productVO = productMapper.getById(orderDTO.getProductId());
            if (productVO == null) {
                throw new IllegalOperationException("商品不存在");
            }
            Product product = new Product();
            BeanUtils.copyProperties(productVO, product);
            product.setId(orderDTO.getProductId());
            product.setStock(-orderDTO.getQuantity());
            try {
                productMapper.update(product);
            } catch (Exception e) {
                throw new IllegalOperationException("商品库存不足");
            }
            amount += product.getPrice() * orderDTO.getQuantity();
            List<ProductItem> productItemList = Collections.singletonList(ProductItem.builder()
                    .id(orderDTO.getProductId())
                    .name(productVO.getName())
                    .image(productVO.getImage())
                    .price(productVO.getPrice())
                    .quantity(orderDTO.getQuantity())
                    .build());
            try {
                itemsJson = objectMapper.writeValueAsString(productItemList);
            } catch (JsonProcessingException e) {
                throw new IllegalOperationException("商品信息错误");
            }
        } else if (orderDTO.getOrderType() == 1) {
            // 购物车订单
            List<ShoppingCartVO> shoppingCartVOList = shoppingCartMapper.getByIds(userId, orderDTO.getShoppingCartIds());
            if (shoppingCartVOList == null || shoppingCartVOList.isEmpty()) {
                throw new IllegalOperationException("选中商品数量为零");
            }
            List<ProductItem> productItemList = new ArrayList<>();
            for (ShoppingCartVO shoppingCartVO : shoppingCartVOList) {
                Long productId = shoppingCartVO.getProductsId();
                Long quantity = shoppingCartVO.getQuantity();
                ProductVO productVO = productMapper.getById(productId);
                if (productVO == null) {
                    throw new IllegalOperationException("商品不存在");
                }
                Product product = new Product();
                product.setId(productId);
                BeanUtils.copyProperties(productVO, product);
                product.setStock(-quantity);
                try {
                    productMapper.update(product);
                } catch (Exception e) {
                    throw new IllegalOperationException("商品" + shoppingCartVO.getProductsName() + "库存不足");
                }
                amount += product.getPrice() * quantity;
                productItemList.add(ProductItem.builder()
                        .id(productId)
                        .name(productVO.getName())
                        .image(productVO.getImage())
                        .price(productVO.getPrice())
                        .quantity(quantity)
                        .build());
            }
            try {
                itemsJson = objectMapper.writeValueAsString(productItemList);
            } catch (JsonProcessingException e) {
                throw new IllegalOperationException("商品信息错误");
            }
        } else {
            throw new IllegalOperationException("订单类型错误");
        }

        String orderId = UUID.randomUUID().toString();

        ShoppingOrder shoppingOrder = new ShoppingOrder();
        shoppingOrder.setUserId(userId);
        shoppingOrder.setReceiverName(addressBook.getReceiverName());
        shoppingOrder.setReceiverGender(addressBook.getSex());
        shoppingOrder.setReceiverPhone(addressBook.getPhone());
        shoppingOrder.setReceiverAddress(addressBook.getProvince() + addressBook.getCity() + addressBook.getDistrict() + addressBook.getDetailAddress());
        shoppingOrder.setDeliveryFee(deliveryRange.getDeliveryFee());
        shoppingOrder.setDeliveryDistance(deliveryRange.getDistance());
        shoppingOrder.setEstimatedDeliveryTime(deliveryRange.getEstimatedDeliveryTime());
        shoppingOrder.setItems(itemsJson);
        shoppingOrder.setRemark(orderDTO.getRemark());
        shoppingOrder.setOrderId(orderId);
        shoppingOrder.setTotalAmount(amount);
        shoppingOrder.setCreateTime(LocalDateTime.now());
        shoppingOrder.setUpdateTime(LocalDateTime.now());

        orderMapper.insert(shoppingOrder);

        if (orderDTO.getOrderType() == 1) {
            shoppingCartMapper.delete(orderDTO.getShoppingCartIds(), userId);
        }
        OrderPayVO orderPayVO = new OrderPayVO();
        orderPayVO.setOrderId(orderId);
        orderPayVO.setDeliveryFee(deliveryRange.getDeliveryFee());
        orderPayVO.setTotalAmount(amount);
        return orderPayVO;
    }

    @Transactional
    public void confirmPayment(OrderPayDTO orderPayDTO) {
        Long userId = BaseContext.getCurrentId();
        ShoppingOrder shoppingOrder = orderMapper.getByOrderId(orderPayDTO.getOrderId(), userId);
        if (shoppingOrder == null) {
            throw new IllegalOperationException("订单不存在");
        }
        if (!Objects.equals(shoppingOrder.getUserId(), userId)) {
            throw new IllegalOperationException("订单不属于当前用户");
        }
        if (Objects.equals(shoppingOrder.getStatus(), ShoppingOrderStatusConstant.CANCELLED)) {
            throw new IllegalOperationException("订单已取消");
        }
        if (!Objects.equals(shoppingOrder.getStatus(), ShoppingOrderStatusConstant.TO_BE_PAY)) {
            throw new IllegalOperationException("订单已支付");
        }
        if (orderPayDTO.getType() == 0) {
            User user = new User();
            user.setId(userId);
            user.setBalance(-shoppingOrder.getTotalAmount());
            userMapper.update(user);
        }
        shoppingOrder.setStatus(1);
        shoppingOrder.setUpdateTime(LocalDateTime.now());
        orderMapper.update(shoppingOrder);
    }

    public PageResult page(OrderPageDTO orderPageDTO) {
        Long userId = BaseContext.getCurrentId();
        orderPageDTO.setUserId(userId);
        PageHelper.startPage(orderPageDTO.getPage(), orderPageDTO.getPageSize());
        Page<ShoppingOrder> page = orderMapper.page(orderPageDTO);
        if (page != null) {
            List<ShoppingOrder> shoppingOrders = page.getResult();
            List<OrderPageViewVO> orderPageViewVOList = new ArrayList<>();
            for (ShoppingOrder shoppingOrder : shoppingOrders) {
                OrderPageViewVO orderPageViewVO = OrderPageViewVO.builder()
                        .id(shoppingOrder.getId())
                        .orderId(shoppingOrder.getOrderId())
                        .status(shoppingOrder.getStatus())
                        .totalAmount(shoppingOrder.getTotalAmount())
                        .createTime(shoppingOrder.getCreateTime().toString())
                        .build();
                List<ProductItem> productItemList;
                try {
                    productItemList = objectMapper.readValue(shoppingOrder.getItems(), new TypeReference<List<ProductItem>>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new IllegalOperationException("商品信息错误");
                }
                orderPageViewVO.setItems(productItemList);
                orderPageViewVOList.add(orderPageViewVO);
            }
            return new PageResult(page.getTotal(), orderPageViewVOList);
        }
        return null;
    }

    public OrderStatisticVO statistics() {
        Long userId = BaseContext.getCurrentId();
        if (userId != null) {
            Long toBePay = orderMapper.countByStatus(ShoppingOrderStatusConstant.TO_BE_PAY, userId);
            Long toBeDelivery = orderMapper.countByStatus(ShoppingOrderStatusConstant.TO_BE_DELIVERY, userId);
            Long deliveryInProgress = orderMapper.countByStatus(ShoppingOrderStatusConstant.DELIVERY_IN_PROGRESS, userId);
            return OrderStatisticVO.builder()
                    .toBePay(toBePay)
                    .toBeDelivery(toBeDelivery)
                    .deliveryInProgress(deliveryInProgress)
                    .build();
        }
        return null;
    }

    public OrderViewVO getViewByOrderId(String orderId) {
        Long userId = BaseContext.getCurrentId();
        ShoppingOrder shoppingOrder = orderMapper.getByOrderId(orderId, userId);
        if (shoppingOrder != null) {
            OrderViewVO orderViewVO = new OrderViewVO();
            BeanUtils.copyProperties(shoppingOrder, orderViewVO);
            orderViewVO.setReceiverSex(shoppingOrder.getReceiverGender());
            try {
                orderViewVO.setItems(objectMapper.readValue(shoppingOrder.getItems(), new TypeReference<List<ProductItem>>() {
                }));
            } catch (JsonProcessingException e) {
                throw new IllegalOperationException("商品信息错误");
            }
            return orderViewVO;
        }
        return null;
    }

    @Transactional
    public void cancel(OrderCancelDTO orderCancelDTO) {
        ShoppingOrder shoppingOrder = orderMapper.getById(orderCancelDTO.getId());
        if (shoppingOrder == null) {
            throw new IllegalOperationException("订单不存在");
        }

        // 用户id为0，代表商家操作，不需要验证用户id
        if (orderCancelDTO.getUserId() != 0) {
            if (!Objects.equals(shoppingOrder.getUserId(), orderCancelDTO.getUserId())) {
                throw new IllegalOperationException("订单不属于当前用户");
            }
        }
        if (shoppingOrder.getStatus().equals(ShoppingOrderStatusConstant.TO_BE_PAY)) {
            // 未支付订单取消，直接取消即可
            shoppingOrder.setStatus(ShoppingOrderStatusConstant.CANCELLED);
            shoppingOrder.setCancelReason(orderCancelDTO.getReason());
            orderMapper.update(shoppingOrder);
        } else if (shoppingOrder.getStatus().equals(ShoppingOrderStatusConstant.TO_BE_DELIVERY)) {
            // 待发货订单取消，需要退款并还原库存
            User user = User.builder()
                    .id(shoppingOrder.getUserId())
                    .balance(shoppingOrder.getTotalAmount())
                    .build();
            userMapper.update(user);
            List<ProductItem> productItemList;
            try {
                productItemList = objectMapper.readValue(shoppingOrder.getItems(), new TypeReference<List<ProductItem>>() {
                });
            } catch (JsonProcessingException e) {
                throw new IllegalOperationException("商品信息错误");
            }
            for (ProductItem productItem : productItemList) {
                Product product = Product.builder()
                        .id(productItem.getId())
                        .stock(productItem.getQuantity())
                        .build();
                productMapper.update(product);
            }
            shoppingOrder.setStatus(ShoppingOrderStatusConstant.CANCELLED);
            shoppingOrder.setCancelReason(orderCancelDTO.getReason());
            orderMapper.update(shoppingOrder);
        } else {
            throw new IllegalOperationException(
                    "订单状态为"
                            +(shoppingOrder.getStatus().equals(ShoppingOrderStatusConstant.DELIVERY_IN_PROGRESS) ?
                                    "配送中" :
                                    shoppingOrder.getStatus().equals(ShoppingOrderStatusConstant.DELIVERY_COMPLETED) ?
                                            "已完成" :
                                            "已取消")
                            + "，不能取消");
        }
    }
}
