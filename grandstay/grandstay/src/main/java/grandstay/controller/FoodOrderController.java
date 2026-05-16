package grandstay.controller;

import grandstay.model.FoodOrder;
import grandstay.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food-orders")
@CrossOrigin(origins = "*")
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    // GET ALL FOOD ORDERS
    @GetMapping
    public List<FoodOrder> getAllFoodOrders() {
        return foodOrderRepository.findAll();
    }

    // PLACE FOOD ORDER
    @PostMapping
    public FoodOrder placeFoodOrder(@RequestBody FoodOrder foodOrder) {

        if (foodOrder.getOrderStatus() == null || foodOrder.getOrderStatus().isEmpty()) {
            foodOrder.setOrderStatus("Pending");
        }

        return foodOrderRepository.save(foodOrder);
    }

    // UPDATE FOOD STATUS
    @PutMapping("/{id}/status")
    public FoodOrder updateFoodOrderStatus(@PathVariable Long id, @RequestParam String status) {

        Optional<FoodOrder> optionalOrder = foodOrderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            FoodOrder existingOrder = optionalOrder.get();
            existingOrder.setOrderStatus(status);
            return foodOrderRepository.save(existingOrder);
        }

        return null;
    }

    // DELETE FOOD ORDER
    @DeleteMapping("/{id}")
    public String deleteFoodOrder(@PathVariable Long id) {

        if (foodOrderRepository.existsById(id)) {
            foodOrderRepository.deleteById(id);
            return "Food order deleted successfully!";
        }

        return "Food order not found!";
    }
}