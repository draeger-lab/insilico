package org.insilico.ui.services.helper;

import java.net.URI;
import java.util.Optional;

import javax.activation.MimeType;

import javafx.geometry.Dimension2D;


/**
 * Interface for a graphic request. This interface is used to tell a graphics provider more about the usage for the graphic.
 * @author roman
 *
 */
public interface IGraphicsRequest {
    /**
     * Returns the item for which a graphic is requested. This value may 
     * be <code>null</code> and can return every possible object.
     * @return
     */
    public Optional<Object> getItem();
    
    /**
     * Returns the mime type which is associated with the item for which a graphic is requested.
     * This value may be <code>null</code> when no mime type is assosicated with the item.
     * @return the {@link MimeType} of the item or <code>null</code> if it hasn't one.
     */
    public Optional<MimeType> getMimeType();
    
    /**
     * Returns the {@link URI} of the resource for which a graphic is requested. 
     * This value may be <code>null</code> if the item is not stored anywhere.
     * @return
     */
    public Optional<URI> getResourceUri();
    
    /**
     * Returns the dimension which the graphic should fill.
     * @return
     */
    public Dimension2D getDimension();
    
    /**
     * Checks if the requested graphic should be monochromatic.
     * @return <code>true</code> if the graphic should be monochromatic.
     */
    public boolean isMonochromGraphicRequested();
}
