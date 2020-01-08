package fr.istic.tools.scanexam.instances

import static fr.istic.tools.scanexam.utils.ScanExamXtendFactory.*

class PFOExams{
	
	def static december19() {
		val X0 = 100
		val X1 = 2450
		val e= exam(
			"PFO_december_19", 
			"/Users/sderrien/Documents/IFSIC/2019-2020/PFO/Examen/dec19/CopiesScan/png",
			6, 
			 #[
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
			/* EX1 : 14 */ 1,2,2,2,2,4,1,
			/* EX2 : 19 "*/ 2,6,3,6,2,
			/* EX3 : 14 */ 4,4,6
		];

		for(i:0..<e.questions.size) {
			e.questions.get(i).weight=weights.get(i)
		}
		e                                                           
	} 
}