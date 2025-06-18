package bg.softuni.bikes_shop.util;

import bg.softuni.bikes_shop.model.dto.ItemDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;


@Component("currentOrder")
@SessionScope
public class CurrentOrder {

    List<ItemDTO> items;
    Double totalPrice;


    public CurrentOrder() {
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void add(ItemDTO newItemDTO) {


        if(this.items==null){
           this.items=new ArrayList<>();
           this.items.add(newItemDTO);
            this.totalPrice=newItemDTO.getPrice()*newItemDTO.getQuantity();
        }else{

         for(ItemDTO item:this.items){
                 if(item.getProductCompositeName().equals(newItemDTO.getProductCompositeName())){
                     int qty=item.getQuantity();
                     item.setQuantity(qty+newItemDTO.getQuantity());
                     this.totalPrice+=newItemDTO.getPrice()*newItemDTO.getQuantity();
                     return;
                     //TODO check for improvement
                 }
            }
            this.items.add(newItemDTO);
            this.totalPrice+=newItemDTO.getPrice()*newItemDTO.getQuantity();

        }


    }
    public void deleteItem(String  itemProductCompositeName ){
        ItemDTO itemDTO=items.stream().filter(i->itemProductCompositeName.equals(i.getProductCompositeName())).findFirst().orElseThrow();
        this.totalPrice-=itemDTO.getPrice()*itemDTO.getQuantity();
        items.remove(itemDTO);
    }

    public void clear() {
        this.items.clear();
        this.totalPrice=0.0;

    }
}
