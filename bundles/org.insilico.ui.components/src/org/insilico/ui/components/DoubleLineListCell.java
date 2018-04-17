package org.insilico.ui.components;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class DoubleLineListCell<T> extends SingleLineListCell<T> {
    public static final String STYLE_CLASS = "double-line-cell";

    private Label subtitleLabel = new Label("TEST");

    public DoubleLineListCell(GraphicStyle gStyle) {
        super(gStyle, STYLE_CLASS);
        subtitleLabel.getStyleClass().add("subtitle");

        getChildren().add(subtitleLabel);
        // setGraphic(subtitleLabel);
    }



    /**
     * Sets the subtitle of this cell.
     * 
     * @param subtitle
     */
    public void setSubtitle(String subtitle) {
        subtitleLabel.setText(subtitle);
    }

    /**
     * Returns the current subtitle of this cell.
     * 
     * @return
     */
    public String getSubtitle() {
        return subtitleLabel.getText();
    }

    /**
     * Returns a observable property for the subtitle string.
     * 
     * @return
     */
    public StringProperty subtitleProperty() {
        return subtitleLabel.textProperty();
    }
}
