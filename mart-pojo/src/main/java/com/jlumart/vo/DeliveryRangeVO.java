package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryRangeVO {
    /**
     * 配送费，isInRange为1时需要
     */
    private Double deliveryFee;
    /**
     * 距离，isInRange为1时需要
     */
    private Long distance;
    /**
     * 预计送达时间，isInRange为1时需要
     */
    private LocalDateTime estimatedDeliveryTime;
    /**
     * 是否在配送范围，0 否 1 是
     */
    private Integer isInRange;
    public DeliveryRangeVO(Long distance) {
        this.distance = distance;
        this.isInRange = distance <= 20000 ? 1 : 0;

        if (isInRange == 1) {
            // 计算配送费：基础5元，每1000米加1元，最多20元
            int baseFee = 5;
            int extraFee = Math.min((int) ((distance / 1000)), 15);
            this.deliveryFee = (double) (baseFee + extraFee);

            // 基础配送时间为 2 天后，每增加 1000 米增加 0.5 天（即半天）
            double baseDays = 1.0;
            double extraDaysPerKm = 0.5;
            double totalDays = baseDays + ((double) distance / 1000) * extraDaysPerKm;

            // 向上取整为整数天
            int roundedDays = (int) Math.ceil(totalDays);

            this.estimatedDeliveryTime = LocalDateTime.now().plusDays(roundedDays);
        }
    }
}
