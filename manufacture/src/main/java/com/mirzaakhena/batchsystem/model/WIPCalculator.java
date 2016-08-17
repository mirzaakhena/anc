package com.mirzaakhena.batchsystem.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WIPCalculator {

	private double ccLB = 0;
	private double ccOH = 0;

	private double finishQuantity = 0;
	private double previousQuantity = 0;
	private double newQuantity = 0;

	private WIPItem previousCost = new WIPItem();
	private WIPItem newCost = new WIPItem();
	private WIPItem subTotal = new WIPItem();

	private WIPItem cpuFG = new WIPItem();
	private WIPItem subTotalFG = new WIPItem();

	private WIPItem cpuWP = new WIPItem();
	private WIPItem subTotalWP = new WIPItem();

	private double euRM = 100;
	private double euLB = 100;
	private double euOH = 100;

	public WIPCalculator() {

	}

	public WIPCalculator(WorkInProcessBalance wipBal) {
		WIPItem newCost = new WIPItem(wipBal.getCostRawMaterial(), wipBal.getCostLabour(), wipBal.getCostOverhead());
		WIPItem preCost = new WIPItem(wipBal.getPrevCostRawMaterial(), wipBal.getPrevCostLabour(), wipBal.getPrevCostOverhead());
		start(wipBal.getNewQuantity(), newCost, wipBal.getPrevQuantity(), preCost);
		finish(wipBal.getFinishQuantity(), wipBal.getConvertionCostLabour(), wipBal.getConvertionCostOverhead());
	}

	public void start(double newQuantity, WIPItem newCost) {
		start(newQuantity, newCost, 0, new WIPItem());
	}

	public void start(double newQuantity, WIPItem newCost, double previousQuantity, WIPItem previousCost) {
		this.newQuantity = newQuantity;
		this.newCost = newCost;
		this.previousCost = previousCost;
		this.previousQuantity = previousQuantity;

		// TOTAL PRODUCTION SCHEDULE

		subTotal.setRm(previousCost.getRm().add(newCost.getRm()));
		subTotal.setLb(previousCost.getLb().add(newCost.getLb()));
		subTotal.setOh(previousCost.getOh().add(newCost.getOh()));

	}

	public void finish(double finishQuantity, double convertionCostLabour, double convertionCostOverhead) {
		this.ccLB = convertionCostLabour;
		this.ccOH = convertionCostOverhead;
		this.finishQuantity = finishQuantity;

		calculate();
	}

	public double getTotalQuantity() {
		return previousQuantity + newQuantity;
	}

	public double getRemainingQuantity() {
		return getTotalQuantity() - finishQuantity;
	}

	private void calculate() {

		// ----------

		double ccRM = 100;

		BigDecimal _100 = new BigDecimal(100);

		BigDecimal finQty = new BigDecimal(finishQuantity);
		BigDecimal remQty = new BigDecimal(getRemainingQuantity());

		// EQUIVALENT UNIT

		double rq = getRemainingQuantity();

		euRM = finishQuantity + (rq * ccRM / 100);
		euLB = finishQuantity + (rq * ccLB / 100);
		euOH = finishQuantity + (rq * ccOH / 100);

		// FINISHED GOODS

		cpuFG.setRm(subTotal.getRm().divide(new BigDecimal(euRM), 10, RoundingMode.HALF_DOWN));
		cpuFG.setLb(euLB == 0 ? BigDecimal.ZERO : subTotal.getLb().divide(new BigDecimal(euLB), 10, RoundingMode.HALF_DOWN));
		cpuFG.setOh(euOH == 0 ? BigDecimal.ZERO : subTotal.getOh().divide(new BigDecimal(euOH), 10, RoundingMode.HALF_DOWN));

		subTotalFG.setRm(cpuFG.getRm().multiply(finQty));
		subTotalFG.setLb(cpuFG.getLb().multiply(finQty));
		subTotalFG.setOh(cpuFG.getOh().multiply(finQty));

		// WORK IN PROCESS

		cpuWP.setRm(cpuFG.getRm().multiply(new BigDecimal(ccRM)).divide(_100));
		cpuWP.setLb(cpuFG.getLb().multiply(new BigDecimal(ccLB)).divide(_100));
		cpuWP.setOh(cpuFG.getOh().multiply(new BigDecimal(ccOH)).divide(_100));

		subTotalWP.setRm(cpuWP.getRm().multiply(remQty));
		subTotalWP.setLb(cpuWP.getLb().multiply(remQty));
		subTotalWP.setOh(cpuWP.getOh().multiply(remQty));

	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();

		sb.append("qty:\t\t");
		sb.append(previousQuantity).append(" + ");
		sb.append(newQuantity).append(" = [ ");
		sb.append(getTotalQuantity()).append(" - ");
		sb.append(finishQuantity).append(" = ");
		sb.append(getRemainingQuantity()).append(" ]");
		sb.append("\n");

		sb.append("convCost:\t");
		sb.append(ccLB).append(" ");
		sb.append(ccOH).append(" ");
		sb.append("\n");

		sb.append("eqUnit:\t\t");
		sb.append(euRM).append(" | ");
		sb.append(euLB).append(" | ");
		sb.append(euOH).append(" | ");
		sb.append("\n");

		sb.append("preCost:\t").append(previousCost).append("\n");
		sb.append("newCost:\t").append(newCost).append("\n");
		sb.append("subtotCost:\t").append(subTotal).append("\n");
		sb.append("cpuFG:\t\t").append(cpuFG).append("\n");
		sb.append("subtotFG:\t").append(subTotalFG).append("\n");
		sb.append("cpuWP:\t\t").append(cpuWP).append("\n");
		sb.append("subtotWP:\t").append(subTotalWP).append("\n");
		sb.append("\n");

		return sb.toString();
	}

}
