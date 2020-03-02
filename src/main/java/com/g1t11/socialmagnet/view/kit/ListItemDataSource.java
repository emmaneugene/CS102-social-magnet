package com.g1t11.socialmagnet.view.kit;

/**
 * Classes that implement {@link ListItemDataSource} can be used as items
 * of a {@link ListView}.
 * <code>ListView</code> calls {@link #render()} on every item of its data 
 * source.
 * @see ListView
 */
public interface ListItemDataSource {
  public void render();
}
