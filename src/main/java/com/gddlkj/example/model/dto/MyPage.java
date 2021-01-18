package com.gddlkj.example.model.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyPage<T> {

    private long current = 1L;

    private long size = 10L;

    private String asc;

    private String desc;

    public Page<T> convertToPage() {
        if (size > 100 || size <= 0)
            size = 100;
        Page<T> page = new Page<>(current, size);
        List<OrderItem> orders = new ArrayList<>();
        if (StringUtils.isNotEmpty(asc))
            orders.add(OrderItem.asc(asc));
        if (StringUtils.isNotEmpty(desc))
            orders.add(OrderItem.desc(desc));
        page.setOrders(orders);
        return page;
    }

}
