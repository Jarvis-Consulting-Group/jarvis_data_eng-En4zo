package ca.jrvs.apps.twitter.dao;

public interface readDao<T, ID> {





    /**
     * Find an entity(Tweet) by its id
     * @param id entity id
     * @return Tweet entity
     */
    T findById(ID id);


}