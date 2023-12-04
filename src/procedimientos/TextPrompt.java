/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Monkeyelgrande
 */
package procedimientos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;
public class TextPrompt extends JLabel implements FocusListener, DocumentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Show {
		ALWAYS, FOCUS_GAINED, FOCUS_LOST;
	}

	private JTextComponent component;
	private Document document;

	private Show show;
	private boolean showPromptOnce;
	private int focusLost;

	public TextPrompt(String text, JTextComponent component) {
		this(text, component, Show.ALWAYS);
	}

	public TextPrompt(String text, JTextComponent component, Show show) {
		this.component = component;
		setShow(show);
		document = component.getDocument();

		setText(text);
		setFont(component.getFont());
		setOpaque(true);
                
//		setForeground(component.getForeground());
		setForeground(Color.gray);
//		setBorder(new EmptyBorder(component.getInsets()));
		setHorizontalAlignment(JLabel.LEADING);

		component.addFocusListener(this);
		document.addDocumentListener(this);

		component.setLayout(new BorderLayout());
		component.add(this);
		checkForPrompt();
	}

	/**
	 * Convenience method to change the alpha value of the current foreground
	 * Color to the specifice value.
	 *
	 * @param alpha
	 *            value in the range of 0 - 1.0.
	 */
	public void changeAlpha(float alpha) {
		changeAlpha((int) (alpha * 255));
	}

	/**
	 * Convenience method to change the alpha value of the current foreground
	 * Color to the specifice value.
	 *
	 * @param alpha
	 *            value in the range of 0 - 255.
	 */
	public void changeAlpha(int alpha) {
		alpha = alpha > 255 ? 255 : alpha < 0 ? 0 : alpha;

		Color foreground = getForeground();
		int red = foreground.getRed();
		int green = foreground.getGreen();
		int blue = foreground.getBlue();

		Color withAlpha = new Color(red, green, blue, alpha);
		super.setForeground(withAlpha);
	}

	/**
	 * Convenience method to change the style of the current Font. The style
	 * values are found in the Font class. Common values might be: Font.BOLD,
	 * Font.ITALIC and Font.BOLD + Font.ITALIC.
	 *
	 * @param style
	 *            value representing the the new style of the Font.
	 */
	public void changeStyle(int style) {
		setFont(getFont().deriveFont(style));
	}
	public Show getShow() {
		return show;
	}
	public void setShow(Show show) {
		this.show = show;
	}

	public boolean getShowPromptOnce() {
		return showPromptOnce;
	}
	public void setShowPromptOnce(boolean showPromptOnce) {
		this.showPromptOnce = showPromptOnce;
	}
	private void checkForPrompt() {
		// Text has been entered, remove the prompt

		if (document.getLength() > 0) {
			setVisible(false);
			return;
		}

		// Prompt has already been shown once, remove it

		if (showPromptOnce && focusLost > 0) {
			setVisible(false);
			return;
		}

		// Check the Show property and component focus to determine if the
		// prompt should be displayed.

		if (component.hasFocus()) {
			if (show == Show.ALWAYS || show == Show.FOCUS_GAINED)
				setVisible(true);
			else
				setVisible(false);
		} else {
			if (show == Show.ALWAYS || show == Show.FOCUS_LOST)
				setVisible(true);
			else
				setVisible(false);
		}
	}

	// Implement FocusListener

	public void focusGained(FocusEvent e) {
		checkForPrompt();
	}

	public void focusLost(FocusEvent e) {
		focusLost++;
		checkForPrompt();
	}

	// Implement DocumentListener

	public void insertUpdate(DocumentEvent e) {
		checkForPrompt();
	}

	public void removeUpdate(DocumentEvent e) {
		checkForPrompt();
	}

	public void changedUpdate(DocumentEvent e) {
	}
}