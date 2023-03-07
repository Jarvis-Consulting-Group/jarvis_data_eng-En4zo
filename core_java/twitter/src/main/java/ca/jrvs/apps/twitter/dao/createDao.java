package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.model.*;
public interface createDao <T,ID>{
    /**
     * Create an entity(Tweet) to the underlying storage
     * @param entity entity that to be created
     * @return created entity
     */
    T create(Root entity);

}