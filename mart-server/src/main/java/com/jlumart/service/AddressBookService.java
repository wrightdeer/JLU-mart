package com.jlumart.service;

import com.jlumart.dto.AddressBookDTO;
import com.jlumart.vo.AddressBookVO;
import com.jlumart.vo.DeliveryRangeVO;

import java.util.List;

public interface AddressBookService {
    List<AddressBookVO> list();

    void add(AddressBookDTO addressBookDTO);

    void edit(AddressBookDTO addressBookDTO);

    void delete(Long id);

    void setDefault(Long id);

    AddressBookVO getDefault();

    DeliveryRangeVO getDeliveryRange(Long id);
}
