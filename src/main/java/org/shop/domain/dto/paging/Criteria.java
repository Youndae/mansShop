package org.shop.domain.dto.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

    private int pageNum;
    private int amount;

    private String keyword;
    private String classification;
    private String type;
    private String sortType;

    public Criteria(){
        this(1, 10);
    }

    public Criteria(int pageNum, int amount){
        this.pageNum = pageNum;
        this.amount = amount;
    }

    public Criteria(int pageNum){
        this.pageNum = pageNum;
        this.amount = 10;
    }


}
