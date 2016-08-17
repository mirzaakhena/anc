package com.mirzaakhena.batchsystem.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WIPItem {

	private BigDecimal rm;
	private BigDecimal lb;
	private BigDecimal oh;

	public WIPItem(double rm, double lb, double oh) {
		this.rm = new BigDecimal(rm);
		this.lb = new BigDecimal(lb);
		this.oh = new BigDecimal(oh);
	}
	
	public WIPItem(BigDecimal rm, BigDecimal lb, BigDecimal oh) {
		this.rm = rm;
		this.lb = lb;
		this.oh = oh;
	}

	public WIPItem() {
		this(0, 0, 0);
	}

	public BigDecimal getTotal() {
		return rm.add(lb).add(oh);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(rm.setScale(2, RoundingMode.UP)).append(" + ");
		sb.append(lb.setScale(2, RoundingMode.UP)).append(" + ");
		sb.append(oh.setScale(2, RoundingMode.UP)).append(" = ");
		sb.append(getTotal().setScale(2, RoundingMode.UP));
		return sb.toString();
	}

}
