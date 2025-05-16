package com.jlumart.service.impl;

import com.jlumart.context.BaseContext;
import com.jlumart.dto.AddressBookDTO;
import com.jlumart.entity.AddressBook;
import com.jlumart.exception.BaiduMapServiceException;
import com.jlumart.mapper.AddressBookMapper;
import com.jlumart.service.AddressBookService;
import com.jlumart.utils.BaiduMapUtil;
import com.jlumart.vo.AddressBookVO;
import com.jlumart.vo.DeliveryRangeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private BaiduMapUtil baiduMapUtil;

    public List<AddressBookVO> list() {
        Long userId = BaseContext.getCurrentId();
        if (userId != null) {
            return addressBookMapper.list(userId);
        }
        return new ArrayList<>();
    }

    public void add(AddressBookDTO addressBookDTO) {
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = new AddressBook();
        BeanUtils.copyProperties(addressBookDTO, addressBook);
        addressBook.setUserId(userId);
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBookMapper.insert(addressBook);
    }

    public void edit(AddressBookDTO addressBookDTO) {
        Long  userId = BaseContext.getCurrentId();
        AddressBook addressBook = new AddressBook();
        BeanUtils.copyProperties(addressBookDTO, addressBook);
        addressBook.setUserId(userId);
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBookMapper.update(addressBook);
    }

    public void delete(Long id) {
        Long userId = BaseContext.getCurrentId();
        addressBookMapper.delete(id, userId);
    }

    @Transactional
    public void setDefault(Long id) {
        Long userId = BaseContext.getCurrentId();
        addressBookMapper.unsetDefaultForUser(userId);
        addressBookMapper.setDefaultForAddress(id, userId);
    }

    public AddressBookVO getDefault() {
        Long userId = BaseContext.getCurrentId();
        if (userId != null) {
            AddressBookVO addressBookVO = addressBookMapper.getDefault(userId);
            if (addressBookVO == null){
                return addressBookMapper.getLatestUpdatedAddress(userId);
            }
            return addressBookVO;
        }
        return null;
    }

    public DeliveryRangeVO getDeliveryRange(Long id) {
        Long userId = BaseContext.getCurrentId();
        if (userId == null || id == null) {
            return null;
        }
        AddressBook addressBook = addressBookMapper.getAddressBook(id, userId);
        if (addressBook == null) {
            return null;
        }
        if(addressBook.getLatitude() == null || addressBook.getLongitude() == null){
            try {
                String address = addressBook.getProvince() + addressBook.getCity() + addressBook.getDistrict() + addressBook.getDetailAddress();
                String latLng = baiduMapUtil.addressToLatLng(address);
                String[] latLngArray = latLng.split(",");
                addressBook.setLatitude(Double.parseDouble(latLngArray[0]));
                addressBook.setLongitude(Double.parseDouble(latLngArray[1]));
            } catch (IOException e) {
                throw new BaiduMapServiceException("地址转换异常，请检查地址是否正确");
            }
            addressBook.setProvince(null);
            addressBook.setCity(null);
            addressBook.setDistrict(null);
            addressBook.setDetailAddress(null);
            addressBookMapper.update(addressBook);
        }
        if (addressBook.getLatitude() != null && addressBook.getLongitude() != null) {
            try {
                Long distance = baiduMapUtil.calculateDistance(baiduMapUtil.getShopAddress(), addressBook.getLatitude() + "," + addressBook.getLongitude());
                return new DeliveryRangeVO(distance);
            } catch (IOException e) {
                return DeliveryRangeVO.builder()
                        .isInRange(0)
                        .build();
            }
        }
        return null;
    }
}
