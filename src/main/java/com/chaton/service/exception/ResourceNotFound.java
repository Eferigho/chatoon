//package com.roadpadi.service.exception;
package com.chaton.service.exception;
import lombok.NoArgsConstructor;

/*
 *@author Ovuefe
 *
 */
@NoArgsConstructor
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String entity, Integer id) {
        super(String.format("%s with the requested id: %s cannot be found", entity, Integer.toString(id)));

    }
    public ResourceNotFound(String entity, String string) {
        super(String.format("%s with the requested %s: %s cannot be found", entity, string,string));

    }
}
