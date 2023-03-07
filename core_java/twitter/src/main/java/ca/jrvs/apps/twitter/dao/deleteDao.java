package ca.jrvs.apps.twitter.dao;



public interface deleteDao<T, ID> {
    /**
     * Delete an entity(Tweet) by its ID
     * @param id of the entity to be deleted
     * @return deleted entity
     */
    T deleteById(ID id);
}