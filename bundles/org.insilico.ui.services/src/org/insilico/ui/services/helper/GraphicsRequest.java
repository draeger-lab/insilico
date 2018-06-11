package org.insilico.ui.services.helper;

import java.net.URI;
import java.util.Optional;

import javax.activation.MimeType;

import javafx.geometry.Dimension2D;

public class GraphicsRequest implements IGraphicsRequest {
    private Optional<Object> item;
    private Optional<MimeType> mime;
    private Optional<URI> uri;
    private Dimension2D dimension;
    private boolean monochrom;
    
    public GraphicsRequest() {
        item = Optional.empty();
        mime = Optional.empty();
        uri = Optional.empty();
        dimension = new Dimension2D(0, 0);
        monochrom = false;
    }
    
    public GraphicsRequest(Object item, MimeType mime, URI uri, Dimension2D dimension, boolean monochrom) {
        this.item = Optional.ofNullable(item);
        this.mime = Optional.ofNullable(mime);
        this.uri = Optional.ofNullable(uri);
        this.monochrom = monochrom;
        
        setDimension(dimension);
    }
    
    public GraphicsRequest(Optional<Object> item, Optional<MimeType> mime, Optional<URI> uri, Dimension2D dimension, boolean monochrom) {
        this.item = item;
        this.mime = mime;
        this.uri = uri;
        this.monochrom = monochrom;
        
        setDimension(dimension);
    }
    
    public GraphicsRequest(IGraphicsRequest copy) {
        this.item = copy.getItem();
        this.mime = copy.getMimeType();
        this.uri = copy.getResourceUri();
        this.monochrom = copy.isMonochromGraphicRequested();
        
        setDimension(copy.getDimension());
    }
    
    public void setItem(Object item) {
        setItem(Optional.ofNullable(item));
    }
    
    public void setItem(Optional<Object> item) {
        this.item = item;
    }

    @Override
    public Optional<Object> getItem() {
        return item;
    }
    
    public void setMimeType(Optional<MimeType> mime) {
        this.mime = mime;
    }
    
    public void setMimeType(MimeType mime) {
        setMimeType(Optional.ofNullable(mime) );
    }

    @Override
    public Optional<MimeType> getMimeType() {
        return mime;
    }
    
    public void setResourceUri(Optional<URI> uri) {
        this.uri = uri;
    }
    
    public void setResourceUri(URI uri) {
        setResourceUri(Optional.ofNullable(uri));
    }

    @Override
    public Optional<URI> getResourceUri() {
        return uri;
    }

    
    public void setDimension(Dimension2D dimension) {
        this.dimension = (dimension != null) ? dimension : new Dimension2D(0, 0);
    }
    
    @Override
    public Dimension2D getDimension() {
        return dimension;
    }
    
    public void setMonochromGraphicRequested(boolean monochrom) {
        this.monochrom = monochrom;
    }

    @Override
    public boolean isMonochromGraphicRequested() {
        return monochrom;
    }

}
