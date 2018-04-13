package org.draegerlab.insilico.ui.components;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

/**
 * Base class for a list cell which displays a single line of text.
 * 
 * A additional {@link GraphicStyle} property can be used to control the style of the cell. The real
 * style of the cell is controlled by the style sheet.
 * 
 * The cell can also have a optional action. This property should be used to add a secondary action
 * to this cell.
 * 
 * @author roman
 *
 * @param <T> The item type this cell displays.
 */
public class SingleLineListCell<T> extends ListCell<T> {

    /**
     * The CSS style class which is used by single line cells. Subclasses of this cell may use a
     * different style class.
     */
    public static final String STYLE_CLASS = "single-line-cell";

    /**
     * Enum type of the possible styles for the graphic of the node.
     * 
     * @author roman
     *
     */
    public enum GraphicStyle {
        /**
         * The graphic node of this cell should not be shown.
         */
        NO_GRAPHIC("no-graphic"),

        /**
         * The graphic of this node should be shown in a square shape.
         */
        SQUARE_GRAPHIC("square-graphic"),

        /**
         * The graphic of this node should be shown in a circle shape.
         */
        CIRCLE_GRAPHIC("circle-graphic");


        /**
         * The style class which will be added when this style is active.
         */
        public final String sytleClass;

        private GraphicStyle(String styleClass) {
            this.sytleClass = styleClass;
        }
    }


    /**
     * 
     * 
     * @author roman
     *
     */
    public enum ActionStyle {
        HIDE_ACTION,
        HOVER_ACTION,
        SHOW_ACTION;
    }


    private ObjectProperty<GraphicStyle> graphicStyle;

    private ObjectProperty<Button> action;


    protected SingleLineListCell(GraphicStyle gStyle, String styleClass) {
        super();
        // Mark with special style class
        if (styleClass != null) {
            getStyleClass().add(styleClass);
        }


        // Add style
        this.graphicStyle = new SimpleObjectProperty<GraphicStyle>(this, "graphicalStyle", gStyle);
        graphicStyle.addListener(node -> {
            SingleLineListCell.this.updateStyleClass();
        });

        graphicProperty().addListener((property, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.getStyleClass().remove("graphic");
            }

            if (newValue != null) {
                newValue.getStyleClass().add("graphic");
            }
        });

        this.updateStyleClass();
    }

    public SingleLineListCell(GraphicStyle gStyle) {
        this(gStyle, STYLE_CLASS);
    }

    public SingleLineListCell() {
        this(GraphicStyle.NO_GRAPHIC);
    }

    protected void updateStyleClass() {
        // Remove all style classes related to graphic style
        getStyleClass().removeAll(Stream.of(GraphicStyle.values()).map(style -> {
            return style.sytleClass;
        }).collect(Collectors.toList()));

        // Set new style class
        getStyleClass().add(graphicStyle.get().sytleClass);
    }

    public final ObjectProperty<Button> actionProperty() {
        if (action == null) {
            action = new StyleableObjectProperty<Button>() {


                @Override
                public Object getBean() {
                    return SingleLineListCell.this;
                }

                @Override
                public String getName() {
                    return "action";
                }

                @Override
                public CssMetaData<? extends Styleable, Button> getCssMetaData() {

                    return null;
                }
            };
        }
        return action;
    }

}
