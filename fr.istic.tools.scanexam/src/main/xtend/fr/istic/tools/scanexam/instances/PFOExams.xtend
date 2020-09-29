package fr.istic.tools.scanexam.instances

import static fr.istic.tools.scanexam.utils.ScanExamXtendFactory.*

class PFOExams{
	
	def static olivier() {
		val X0 = 100
		val X1 = 2450
		
		val students = #[
			"ALLA Haphet",
			"ARQUES Alexandre",
			"BONNEAU Quentin",
			"CHEVALIER Michel",
			"CHILLET Alice",
			"DE TAEYE Caroline",
			"DELPORTE Kevin",
			"DENES PIERRE",
			"DUPIN Lucas",
			"ETRILLARD Olivier",
			"GUILBAUD Valentin",
			"KLEIN Nicolas",
			"LASSOUT Leo",
			"LE PERON-SLAES Alix",
			"MAITRE Kilian",
			"MALHERBE Tom",
			"MANDELLI Zoe",
			"MOLLARD Arnaud",
			"MROUEH Celia",
			"NADAL Sophie",
			"PLUMIER Quentin",
			"POULLOT Amelie",
			"ROGER Benoit-Joseph",
			"SCIASCIA Hugo",
			"SOURY Theo",
			"THUILLIER Corentyn",
			"THURA Remy",
			"TOURTEAU Remy",
			"TURBAULT Adrien",
			"UMENHOVER Lola"
			
		]
		val e= exam(
			"DS_CCI", 
			"./CopieScanOSentieys/png",
			6,
			42,
			 #[
				question("NAME",#[X0,0,X1,800,1], #[30,80,80,60,0], students,0),
				question("EX1_Q1",#[X0,1200,X1,2900,1], #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),
				question("EX1_Q2",#[X0,2700,X1,3250,1], #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),
				question("EX1_Q3",#[X0,300,X1,950,2],  #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),

				question("EX2_Q1",#[X0,2100,X1,2600,2], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q2",#[X0,2500,X1,3050,2], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q3",#[X0,100,X1,2400,3], #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q4",#[X0,2500,X1,3300,3], #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q5",#[X0,450  ,X1,1000,4], #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q6",#[X0,1000,X1,1500,4], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q7",#[X0,1550,X1,2400,4], #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q8",#[X0,2300,X1,2800,4], #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q9",#[X0,2700,X1,3250,4], #[30,80,80,60,0],  #["A", "B", "C", "D", "E", "F"],2),


				                                         
				question("EX3_Q1",#[X0,1300,X1,1800,5],  #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX3_Q2",#[X0,1700,X1,2100,5], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX3_Q3",#[X0,0,X1,3150,5], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],5)
			]
		)
		val weights = #[	
			0,
			/* EX1 : 14 */ 1,2,2,
			/* EX2 : 19 "*/ 1,6,3,6,2,2,2,9,10,
			/* EX3 : 13 */ 4,3,6
		];

		for(i:0..<e.questions.size) {
			e.questions.get(i).weight=weights.get(i)
		}
		e
	}
	def static december19() {
		val X0 = 100
		val X1 = 2450
		val e= exam(
			"PFO_december_19", 
			"./examples/scan/",
			6,
			42,
			 #[
				question("ID",#[X0,0,X1,400,1], #[30,80,80,60,0], #["1"],1),
				question("EX1_Q1",#[X0,2400,X1,2750,1], #[30,80,80,60,0], #["F", "A"],1),
				question("EX1_Q2",#[X0,1600,X1,3200,1], #[30,80,80,60,0], #["F", "C", "A"],2),
				question("EX1_Q3",#[X0,280,X1,650,2],   #[30,80,80,60,0], #["F", "D", "C", "B", "A"],4),
				question("EX1_Q4",#[X0,700,X1,1350,2],  #[30,80,80,60,0], #["F", "D", "C", "B", "A"],2),
				question("EX1_Q5",#[X0,1450,X1,2200,2], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX1_Q6",#[X0,2275,X1,3000,2], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX1_Q7",#[X0,3000,X1,3170,2], #[30,80,80,60,0], #["F", "A"],1),
				                                         
				question("EX2_Q1",#[X0,2350,X1,3300,3], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q2",#[X0,500,X1,1900,4],  #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q3",#[X0,2100,X1,3000,4], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q4",#[X0,450,X1,1800,5],  #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX2_Q5",#[X0,2000,X1,3000,5], #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],5),
				                                                 
				question("EX3_Q1",#[X0,500,X1,1050,6],  #[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX3_Q2a",#[X0,1450,X1,1900,6],#[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2),
				question("EX3_Q2b",#[X0,1900,X1,3250,6],#[30,80,80,60,0], #["A", "B", "C", "D", "E", "F"],2)
			]                                                       
		)
		val weights = #[	
			0,
			/* EX1 : 14 */ 1,2,2,2,2,4,1,
			/* EX2 : 19 "*/ 2,6,3,6,2,
			/* EX3 : 13 */ 4,3,6
		];

		for(i:0..<e.questions.size) {
			e.questions.get(i).weight=weights.get(i)
		}
		e                                                           
	} 
	
	def static void main(String[] args) {
		val dec19 = december19
		ExamIO.save(dec19,"examples/"+dec19.label+".xmi");
		val eii = olivier
		ExamIO.save(eii,"examples/"+eii.label+".xmi");
		
	}
}