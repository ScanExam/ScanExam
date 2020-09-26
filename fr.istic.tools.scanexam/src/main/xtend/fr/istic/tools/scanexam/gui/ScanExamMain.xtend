package fr.istic.tools.scanexam.gui

import fr.istic.tools.scanexam.instances.ExamIO
import fr.istic.tools.scanexam.utils.ScanExamExcelBackend
import fr.istic.tools.scanexam.utils.ScanExamXtendFactory
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.List
import java.util.Timer
import java.util.TimerTask
import java.util.function.Function
import javax.swing.AbstractAction
import javax.swing.ActionMap
import javax.swing.BoxLayout
import javax.swing.InputMap
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTabbedPane
import javax.swing.KeyStroke

class ScanExamMain {
//Ceci est un commentaire dans la class Main 
	def static mapKeyAction(String name, JPanel buttonPanel, int e, int i, Function<ActionEvent, Object> action) {
		val KeyStroke prevStudentKS = KeyStroke.getKeyStroke(e, i, true);
		val InputMap inputMap = buttonPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		val ActionMap actionMap = buttonPanel.getActionMap();
		inputMap.put(prevStudentKS, name);
		actionMap.put(name, new AbstractAction {
			override actionPerformed(ActionEvent e) { action.apply(e) }
		});
	}

	def static protected JComponent makeTextPanel(String text) {
		var JPanel panel = new JPanel(false)
		var JLabel filler = new JLabel(text)
		filler.setHorizontalAlignment(JLabel.CENTER)
		panel.setLayout(new GridLayout(1, 1))
		panel.add(filler)
		return panel
	}


	def static void main(String[] args) throws IOException {


        
		val JFrame frame = new JFrame("Scan Exam");
		frame.setSize(1600, 800);

		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));

		val JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		val JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));

		val JPanel leftPane = new JPanel();
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));

		val JPanel rightPane = new JPanel();
		rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));

		topPane.add(leftPane)
		topPane.add(rightPane)
	
		var JTabbedPane tabbedPane = new JTabbedPane()
		
		tabbedPane.addTab("Correction mode", null, contentPane, "Does nothing")
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_F1)
		frame.setContentPane(tabbedPane);

	

//		tabbedPane.addTab("Edition mode", null, templateMode, "Does nothing")
//		tabbedPane.setMnemonicAt(0, KeyEvent.VK_F2)
 
		val exam = 
		try {
			if (args.empty) {
				JOptionPane.showMessageDialog(null, "argument missing\nusage : scanexam file", "InfoBox: ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			ExamIO.load(args.get(0))}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not open exam model file "+args.get(0), "InfoBox: ", JOptionPane.ERROR_MESSAGE);
			return 
		}

		val data = 
		try {
			ScanExamXtendFactory.gradingData(exam)
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, exception.class.simpleName+":"+exception.message, "InfoBox: ", JOptionPane.ERROR_MESSAGE);
			return
		}
		val controler = new ScanExamController(data)

		val Timer timer = new Timer(); 
        val TimerTask task = new BackupTask(controler); 
        timer.schedule(task,150000,150000); 

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			override void windowClosing(WindowEvent e) {
				val frame = e.getSource() as JFrame;

				val result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the application?",
					"Exit Application", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					val DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
					val data_ = df.format(new Date());
					ScanExamXtendUtils.save(new File('''./backups/«exam.label»_«data_».xmi'''), data)
					ScanExamExcelBackend.save(new File('''./backups/«exam.label»_«data_».xls'''), data)
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
		});

		frame.setVisible(true);
		val ScanExamPanel scanPanel = new ScanExamPanel(controler)
		leftPane.add(new JScrollPane(scanPanel))
		rightPane.add(new ExcelTableViewer(controler))

		scanPanel.setSize(1600, 400)

		val JPanel buttonPanel = new JPanel()

		var JButton prev = new JButton("next Q")
		prev.addActionListener([ActionEvent e|controler.getNextQuestion()])
		buttonPanel.add(prev)

		var JButton next = new JButton("prev Q")
		next.addActionListener([ActionEvent e|controler.getPrevQuestion()])
		buttonPanel.add(next)

		var JButton prevStudent = new JButton("prev student")
		prevStudent.addActionListener([ActionEvent e|controler.prevExam()])
		buttonPanel.add(prevStudent)

		var JButton nextStudent = new JButton("next student")
		nextStudent.addActionListener([ActionEvent e|controler.nextExam()])
		buttonPanel.add(nextStudent)

		var JButton loadXMI = new JButton("load grades ")
		loadXMI.addActionListener([ActionEvent e|controler.loadXMI()])
		buttonPanel.add(loadXMI)

		var JButton saveXMI = new JButton("save grades")
		saveXMI.addActionListener([ActionEvent e|controler.saveXMI()])
		buttonPanel.add(saveXMI)

		var JButton loadExcel = new JButton("load Excel ")
		loadExcel.addActionListener([ActionEvent e|controler.loadExcel()])
		buttonPanel.add(loadExcel)

		var JButton saveExcel = new JButton("save Excel ")
		saveExcel.addActionListener([ActionEvent e|controler.saveExcel()])
		buttonPanel.add(saveExcel)

		contentPane.add(topPane, "North")
		contentPane.add(buttonPanel, "South")

		val KeyStroke validateKS = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
		val KeyStroke nextAndValidateKS = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.META_DOWN_MASK, true);
		val KeyStroke incGrade = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);
		val KeyStroke lowerGrade = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);

		val KeyStroke saveGrades = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK, true);

		val KeyStroke nextQuestion = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.META_DOWN_MASK, true);
		val KeyStroke prevQuestion = KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.META_DOWN_MASK, true);
		val KeyStroke nextStudentKS = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
		val KeyStroke prevStudentKS = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);

		val KeyStroke gotoStudentKS = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK, true);

		// Get the input map for our component
		// In this case we are interested in key strokes in the focused window
		val InputMap inputMap = buttonPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		// Get the action map for our component
		val ActionMap actionMap = buttonPanel.getActionMap();

		#[
			#[KeyEvent.VK_1, 0],
			#[KeyEvent.VK_2, 1],
			#[KeyEvent.VK_3, 2],
			#[KeyEvent.VK_4, 3],
			#[KeyEvent.VK_5, 4],
			#[KeyEvent.VK_6, 5],
			#[KeyEvent.VK_7, 6],
			#[KeyEvent.VK_8, 7]
		].forEach [ List<Integer> p |
			mapKeyAction(
				"SetGradeAndValidate_" + p.get(1),
				buttonPanel,
				p.get(0),
				InputEvent.SHIFT_DOWN_MASK,
				[ e |
					controler.grade = p.get(1)
					controler.validateGrade;
					controler.nextExam; 
					true
				]
			)
		]

		#[
			#[KeyEvent.VK_1, 0],
			#[KeyEvent.VK_2, 1],
			#[KeyEvent.VK_3, 2],
			#[KeyEvent.VK_4, 3],
			#[KeyEvent.VK_5, 4],
			#[KeyEvent.VK_6, 5],
			#[KeyEvent.VK_7, 6],
			#[KeyEvent.VK_8, 7]
		].forEach [ List<Integer> p |
			mapKeyAction(
				"SetGrade_" + p.get(1),
				buttonPanel,
				p.get(0),
				0,
				[ e |
					controler.grade = p.get(1)
					true
				]
			)
		]

		inputMap.put(gotoStudentKS, "gotoStudent");
		actionMap.put("gotoStudent", new AbstractAction {
			override actionPerformed(ActionEvent e) { 
			val firstNumber = JOptionPane.showInputDialog("Enter student ID");
			try {
				val int studentId= Integer.parseInt(firstNumber);
				controler.gotoStudent(studentId)
			} catch (Exception exception) {
				
			}
		}
		});

		inputMap.put(nextStudentKS, "nextStudent");
		actionMap.put("nextStudent", new AbstractAction {
			override actionPerformed(ActionEvent e) { controler.nextExam }
		});

		// Map the key stroke to our "action key" (see below)
		inputMap.put(prevStudentKS, "prevStudent");
		actionMap.put("prevStudent", new AbstractAction {
			override actionPerformed(ActionEvent e) { controler.prevExam }
		});

		// Map the key stroke to our "action key" (see below)
		inputMap.put(nextQuestion, "nextQuestion");
		// Add the required action listener to out action map
		actionMap.put("nextQuestion", new AbstractAction {
			override actionPerformed(ActionEvent e) { controler.nextQuestion }
		});

		// Map the key stroke to our "action key" (see below)
		inputMap.put(prevQuestion, "prevQuestion");
		actionMap.put("prevQuestion", new AbstractAction {
			override actionPerformed(ActionEvent e) { controler.prevQuestion }
		});

		// Map the key stroke to our "action key" (see below)
		inputMap.put(nextAndValidateKS, "nextAndValidateKS");
		// Add the required action listener to out action map
		actionMap.put("nextAndValidateKS", new AbstractAction {
			override actionPerformed(ActionEvent e) {
				controler.validateGrade
				controler.nextExam
			}
		});

		// Map the key stroke to our "action key" (see below)
		inputMap.put(validateKS, "validateKS");
		// Add the required action listener to out action map
		actionMap.put("validateKS", new AbstractAction {
			override actionPerformed(ActionEvent e) {
				controler.validateGrade
			}
		});

		// Map the key stroke to our "action key" (see below)
		inputMap.put(incGrade, "incGrade");
		// Add the required action listener to out action map
		actionMap.put("incGrade", new AbstractAction {
			override actionPerformed(ActionEvent e) {
				controler.increaseGrade
			}
		});

		// Map the key stroke to our "action key" (see below)
		inputMap.put(lowerGrade, "lowerGrade");
		// Add the required action listener to out action map
		actionMap.put("lowerGrade", new AbstractAction {
			override actionPerformed(ActionEvent e) {
				controler.decreaseGrade
			}
		});

		// Map the key stroke to our "action key" (see below)
		inputMap.put(saveGrades, "saveGrades");
		// Add the required action listener to out action map
		actionMap.put("saveGrades", new AbstractAction {
			override actionPerformed(ActionEvent e) {
				controler.saveExcel
			}
		});

		frame.setLocation(200, 200)
		frame.setVisible(true)
	}
}
 
class BackupTask extends TimerTask{
	
	ScanExamController controller

	override run() {
		val DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		val data_ = df.format(new Date());
		controller.saveExcel(new File('''backups/backup_«controller.gradingData.exam.label»_«data_».xls'''))
	}
	
	new(ScanExamController controller) {
		this.controller=controller;
	}
}