package org.example.Barter;

import org.example.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/barters")
public class BarterController {
    @Autowired
    private BarterService barterService;
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> newProposal(@RequestBody BarterRequest tradeProposal){
        return new ResponseEntity<>(barterService.saveProduct(tradeProposal), HttpStatus.CREATED);
    }
    @GetMapping("/{barter_id}")
    public ResponseEntity<BarterDTO> getBarterById(@PathVariable("barter_id") Integer barterId) {
        Barter barter = barterService.getBarterById(barterId);
        if (barter != null) {
            BarterDTO barterDTO = BarterDTO.fromEntity(barter);
            return new ResponseEntity<>(barterDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{barter_id}/status")
    public ResponseEntity<String> updateBarterStatus(
            @PathVariable("barter_id") Integer barterId,
            @RequestBody BarterStatusUpdateRequest request,
            @AuthenticationPrincipal User userDetails) {

        Barter barter = barterService.getBarterById(barterId);
        if (barter == null) {
            return new ResponseEntity<>("Barter not found", HttpStatus.NOT_FOUND);
        }
        if (!isUserAuthorized(userDetails, barter)) {
            return new ResponseEntity<>("Not authorized to update status", HttpStatus.valueOf(403));
        }
        String newStatus = request.getStatus().toLowerCase();
        barter.setStatus(newStatus);
        barterService.updateStatus(barter);
        return new ResponseEntity<>("Status updated successfully", HttpStatus.OK);
    }

    private boolean isUserAuthorized(User userDetails, Barter barter) {
        return userDetails.getId().equals(barter.getOfferedId().getSeller().getId()) || userDetails.getId().equals(barter.getRequestedId().getSeller().getId()) ;
    }

    /** TO DO:
     *
    @GetMapping("/{proposal_id}")
    public ResponseEntity<List<ProductDTO>> getProducts(
            @PathVariable Integer proposal_id
    ){
        Optional<TradeProposal> product = productRepository.findById(product_id);
        List<ProductDTO> p = product.stream().map(ProductDTO::fromProduct).toList();
        return new ResponseEntity<>(p, HttpStatus.OK);
    } **/


}
