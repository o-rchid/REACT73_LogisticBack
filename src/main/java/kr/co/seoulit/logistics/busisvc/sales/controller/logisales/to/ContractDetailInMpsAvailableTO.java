package kr.co.seoulit.logistics.busisvc.sales.controller.logisales.to;

import kr.co.seoulit.logistics.logiinfosvc.compinfo.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContractDetailInMpsAvailableTO extends BaseTO {

	private String contractNo;
	private String contractType;
	private String contractDate;
	private String customerCode;
	private String contractDetailNo;
	private String itemCode;
	private String itemName;
	private String unitOfContract;
	private String estimateAmount;
	private String stockAmountUse;
	private String productionRequirement;
	private String dueDateOfContract;
	private String description;
	private String planClassification;
	private String mpsPlanDate;
	private String scheduledEndDate;

}
