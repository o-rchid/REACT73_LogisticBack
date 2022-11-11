package kr.co.seoulit.logistics.busisvc.sales.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.logistics.busisvc.sales.to.DeliveryInfoTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper {

	public ArrayList<DeliveryInfoTO> selectDeliveryInfoList();

	public void deliver(HashMap<String, Object> map);

	public void insertDeliveryResult(DeliveryInfoTO TO);

	public void updateDeliveryResult(DeliveryInfoTO TO);

	public void deleteDeliveryResult(DeliveryInfoTO TO);

}
