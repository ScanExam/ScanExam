package fr.istic.tools.scanexam.export;

public class GradesExportTest {

	/*//GradesExport exporter;
	String examName = "torchon_pfo";
	List<StudentSheet> sheetsFromStudents = new ArrayList<>();
	GradesExportImpl exporter = new GradesExportImpl(null);
		
	
	@BeforeEach
	void init() {
		
		DataFactory df = new DataFactory();
		
		List<Integer> pages = List.of(1,2,3,4);
		
		
		for(int i=0; i<10; i++) {
			
			Grade grade = CoreFactory.eINSTANCE.createGrade();
			Random r = new Random();
			grade.getEntries().add(df.createGradeEntry(i, "", r.nextInt(5)));
						
			StudentSheet temp = df.createStudentSheet(0, pages);
			temp.setStudentName("Etudiant"+i);
			temp.getGrades().add(grade);
			temp.computeGrade();
			
			sheetsFromStudents.add(temp);
		}
	}
	
	/*
	 * Méthode à utiliser quand on reviendra sur les tests d'export plus propre
	 * 	
	private Object jailBreak(Class<?> clazz, String name, Object[] args, Class<?>[] argClasses) {
		try {
			
			Method method = clazz.getDeclaredMethod(name, argClasses);
			method.setAccessible(true);
			return method.invoke(clazz, args);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
	/*@Test
	@DisplayName("Test de la méthode d'export des notes au format Excel xlsx")
	void exportGradesTest() {
		exporter.exportGradesPrivate(sheetsFromStudents, examName);
	}*/
}
