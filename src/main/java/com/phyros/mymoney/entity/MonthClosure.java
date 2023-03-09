package com.phyros.mymoney.entity;

import java.time.LocalDate;
import java.util.List;

public class MonthClosure {
	
	private String title;
	
	private String description;
	
	//Total de crédito - obtido durante o período
	
	//Total de débito - gastos do período
	
	//Líquido
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private List<Movement> movements;

}


