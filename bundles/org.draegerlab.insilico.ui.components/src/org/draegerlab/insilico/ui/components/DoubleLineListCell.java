package org.draegerlab.insilico.ui.components;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class DoubleLineListCell<T> extends ListCell<T> {
	
	private Label subtitleLabel = new Label();
	
	public DoubleLineListCell() {
		super();
		getStyleClass().add("double-line-cell");
		subtitleLabel.getStyleClass().add("subtitle");
	}
	
	public void setSubtitle(String subtitle) {
		
	}

	public Label getSubtitleLabel() {
		return subtitleLabel;
	}

	public void setSubtitleLabel(Label subtitleLabel) {
		this.subtitleLabel = subtitleLabel;
	}
}
