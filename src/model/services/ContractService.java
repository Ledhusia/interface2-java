package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

    private OnlinePaymentService paymentService;

    public ContractService(OnlinePaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void processContract(Contract contract, int months) {
        
        double basePortion = contract.getTotalValue() / months;

        for(int i = 1; i <= months; i++) {

            LocalDate dueDate = contract.getDate().plusMonths(i);

            double interest = paymentService.interest(basePortion, i);
            double fee = paymentService.paymentFee(basePortion + interest);
            double portion = basePortion + interest + fee;

            Installment installment = new Installment(dueDate, portion);
            contract.getInstallments().add(installment);
        }
        
        

    }
}
