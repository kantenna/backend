package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jpa.entity.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 테이블명 : itemtbl
// 컬럼 : 상품코드(ID)(code - P0001), 상품명(item_nm), 가격(item_price), 재고수량(stock_number)
//        상세설명(item_detail), 판매상태(item_sell_status) : SELL, SOLDOUT
//        등록시간, 수정시간

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "itemtbl")
@Entity
public class Item extends BaseEntity{

    @Id
    private String code;

    @Column(nullable = false)
    private String itemNm;

    @Column(nullable = false)
    private int itemPrice;

    @Column(nullable = false)
    private int stockNumber;

    private String itemDetail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    public void changeItemSellStatus(ItemSellStatus itemSellStatus) {
        this.itemSellStatus = itemSellStatus;
    }

    public void changeStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }
}
