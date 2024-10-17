package org.shop.domain.dto.paging;

import lombok.*;


@Getter
@ToString
@AllArgsConstructor
public class Criteria {

    private int pageNum;
    private int amount;

    private int offset;

    private String keyword;

//    private String searchType;

    public Criteria(){
        this.pageNum = 1;
        this.amount = 10;
        this.offset = 0;
    }

    public Criteria(int pageNum, int amount, String keyword) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.offset = (pageNum - 1) * amount;
        this.keyword = keyword;
    }

    public Criteria(int pageNum, int amount){
        this.pageNum = pageNum;
        this.amount = amount;
        this.offset = (pageNum - 1) * amount;
    }

    public Criteria(int pageNum){
        this.pageNum = pageNum;
        this.amount = 10;
        this.offset = (pageNum - 1) * 10;
    }


}
