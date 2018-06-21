package org.insilico.ui.services;

import java.util.Map;
import java.util.function.Predicate;

import org.insilico.ui.services.helper.IGraphicsRequest;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;


/**
 * A {@link GraphicLookup} can answer a {@link IGraphicsRequest} with a {@link Node} that suites the need.
 * @author roman
 *
 */
public interface GraphicLookup extends Predicate<GraphicLookup.IRequest> {
    public interface IRequest {
        public void getContext();
        public Dimension2D getDimension();
        public Map<String, Object> getAttributes();
        public Object getAttribute(String key);
    }
    
    public Node lookupGraphic(GraphicLookup.IRequest request);
}
