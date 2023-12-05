package org.example.Barter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Product.Product;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TradeProposal")
public class Barter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proposal_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "offeredProduct_id")
    private Product offeredId;
    @ManyToOne
    @JoinColumn(name = "requestedProduct_id")
    private Product requestedId;
    @Column(name = "status")
    private String status;
    @Column(name = "initiated_at")
    private LocalDateTime initiated_at;
    @Column(name = "last_updated")
    private LocalDateTime last_updated;
    @Column(name = "message")
    private String message;
}
