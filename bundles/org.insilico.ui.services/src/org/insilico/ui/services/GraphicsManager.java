package org.insilico.ui.services;

import java.util.Optional;

import org.eclipse.core.resources.IResource;
import org.insilico.ui.services.helper.GraphicsRequest;
import org.insilico.ui.services.helper.IGraphicsRequest;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;

/**
 * A {@link GraphicsManager} is responsible for requesting and 
 * providing graphic nodes for various resources.
 * 
 * @author roman
 *
 */
public interface GraphicsManager {
    public Optional<Node> getGraphic(IGraphicsRequest request);
    
    /**
     * Requests a graphic for the given {@link IResource}, which fills the given {@link Dimension2D}.
     * @param resource the resource for which the graphic is requested.
     * @param dimension the dimension which should be covered by the graphic.
     * @param monochrom if <code>true</code> the graphic should be monochromatic.
     * @return A {@link Node} which can be used as graphic to decorate a visual element which 
     * is associated with this resource.
     */
    public default Optional<Node> getGraphic(IResource resource, Dimension2D dimension, boolean monochrom) {
        IGraphicsRequest req = new GraphicsRequest(resource, null, resource.getLocationURI(), dimension, monochrom);
        return getGraphic(req);
    }
    
    
}
