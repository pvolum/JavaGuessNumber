
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Guesser extends JPanel implements ActionListener{
	private JFrame frame;
	private JTextField textField;
	private JButton playAgain;
	private JTextArea promt;
	private JLabel label;
	private Random r = new Random();
	private int target;
	private int prevDif;
	private Integer guess;
	private Integer prevGuess;

	public Guesser(){	//constructer to instantiate all member data appropriatley 
		prevGuess= null;
		this.setBackground(Color.WHITE);
		promt = new JTextArea(3, 20);
		promt.setEditable(false);	//should never change
		
		promt.setText("I have a number between 1 and 1000. Can you guess my number?\nPlease enter your first guess.");
		promt.setBackground(this.getBackground());	//button is not added until first round over
		playAgain = new JButton("Play Again");
		label = new JLabel();
		textField = new JTextField(3);
		
		textField.addActionListener(this);
		label.setText("waiting on input");
		add(promt);
		add(label);
		add(textField);
		target = r.nextInt(1000) + 1;
		System.out.print(target);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		//action listener for number entry 
		String s = textField.getText();
		guess = Integer.parseInt(s);
		
		int dif = Math.abs(guess - target);//for warmer and closer

		if (prevGuess != null){
			if (prevDif < dif)
				setColor('b');
			else if (prevDif > dif)
				setColor('r');
			else
				setColor('w');	//same proximity to answer -> set to white
		}
		//handle label changes to help user hone in on targer
		if (guess > target)
			label.setText("Too High");
		if (guess < target)
			label.setText("Too Low");
		//defines what is to be done if user guesses the target 
		if (guess == target){
			this.add(playAgain);
			playAgain.show();	//second run not needed, afterwards it is
			textField.setEditable(false);
			label.setText("Correct!");
			playAgain.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					setBackground(Color.WHITE);
					promt.setBackground(getBackground());
					textField.setEditable(true);
					target = r.nextInt(1000) + 1;
					System.out.print(target);
					guess=null;
					prevGuess = null;
					label.setText("Waiting on input");
					textField.setText("");
					playAgain.hide();					
				}
				
			});

		}
		//increment difference to previous difference in order to keep track of color changes
		prevDif = dif;
		prevGuess = guess;
	}
	

	//simple function that sets color depending on character sent in
	public void setColor(char color){
		if (color =='r'){
			this.setBackground(Color.RED);
			
		}
		else if (color == 'b'){
			this.setBackground(Color.BLUE);
		}
		else if (color == 'w'){
			this.setBackground(Color.WHITE);
		}
		//changes promt color for uniformity sake 
		promt.setBackground(this.getBackground());
	}

	//initial User Interface set up, only called once 
	private void showGUI(){
		this.frame = new JFrame("Guess Game");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(600, 600);
		this.frame.add(this);
		this.frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Guesser g = new Guesser();
		g.showGUI();   
	}
}
