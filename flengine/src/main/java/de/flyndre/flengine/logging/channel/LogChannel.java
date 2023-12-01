package de.flyndre.flengine.logging.channel;

/**
 * A {@code LogChannel} is a way to enable and disable logging to different location.
 * It can be thought of as a literal channel that can be opened and closed.
 * This is typically done by adding and removing {@code Handler}s to/from a specified {@code Logger}.
 * @author David
 */
public interface LogChannel {

    /**
     * Set wheter the {@code Channel} is open.
     * If already opened or closed, no action should be taken.
     * @param open the new <i>open</i> status
     */
    void setOpen(boolean open);

    /**
     * States whether the {@code Channel} is currently open.
     * @return the current <i>open</i> status
     */
    boolean isOpen();
}
