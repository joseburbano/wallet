package test.wallet.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Clase que ofrece los atributos básicos que se devuelven en
 * funcionalidades que requieren paginación
 *
 * @param <T> Tipo de dato que encapsula la paginación
 */
@Setter
@Getter
@Builder
public class CustomSlice <T> {
    private List<T> elements;
    private int size;
    private int page;
    private int totalPage;
    private long totalElements;
    private boolean sorted;
    private boolean paged;
    private boolean first;
    private boolean last;
}
