package br.com.tsmweb.biblioteca.models.repository.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class Pagina<T> {
	
	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	private int pageSize;
    private List<PageItem> items;
    private int currentNumber;
    
	public Pagina(Page<T> page, int pageSize, HttpServletRequest httpServletRequest ) {	
		this.page = page;
		String httpUrl = httpServletRequest.getRequestURL().append(httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "").toString().replaceAll("\\+", "%20");
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
		this.pageSize = pageSize;
		
	    items = new ArrayList<PageItem>();

	    currentNumber = page.getNumber() + 1; //start from 1 to match page.page

	    int start, size;
	    
	    if (page.getTotalPages() <= pageSize){
	        start = 1;
	        size = page.getTotalPages();
	    } else {
	       if (currentNumber <= pageSize - pageSize/2){
	           start = 1;
	           size = pageSize;
	       } else if (currentNumber >= page.getTotalPages() - pageSize/2){
	           start = page.getTotalPages() - pageSize + 1;
	           size = pageSize;
	       } else {
	           start = currentNumber - pageSize/2;
	           size = pageSize;
	       }
	    }
	    
        for (int i = 0; i<size; i++){
            items.add(new PageItem(start+i, (start+i)==currentNumber));
        }
	}

    public List<PageItem> getItems(){
	     return items;
    }
	
	public List<T> getConteudo() {
		return page.getContent();
	}
	
	public boolean isVazia() {
		return page.getContent().isEmpty();
	}
	
	public int getAtual() {
		return page.getNumber();
	}
	
	public int getNumber() {
		return currentNumber;
	}
	
	public boolean isPrimeira() {
		return page.isFirst();
	}
	
	public boolean isUltima() {
		return page.isLast();
	}
	
	public int getTotal() {
		return page.getTotalPages();
	}	
	
	public long getTotalElementos() {
		return page.getTotalElements();
	}
	
	public int getSize() {
		return page.getSize();
	}
	
	public boolean isFirstPage(){
        return page.isFirst();
    }

    public boolean isLastPage(){
        return page.isLast();
    }

    public boolean isHasPreviousPage(){
        return page.hasPrevious();
    }

    public boolean isHasNextPage(){
        return page.hasNext();
    }
	
	public String urlParaPagina(int size, int pagina, String sort, String dir) {
		return uriBuilder.replaceQueryParam("page", pagina, "size", size, "sort", sort, "dir", dir ).build(true).encode().toUriString();
	}
	
	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder
				.fromUriString(uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s", propriedade);
		String valorDir = String.format("%s", inverterDirecao(propriedade));
		
		return uriBuilderOrder.replaceQueryParam("sort", valorSort)
				              .replaceQueryParam("dir", valorDir)
				              .build(true).encode().toUriString();
	}
	
	public String inverterDirecao(String propriedade) {
		String direcao = "asc";
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null; 
		
		if (order != null) { 
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc"; 
	    }
		
		return direcao;
	}
	
	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null; 
		
		if (order == null) {
			return false;
		}
		
		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}
	
	public class PageItem {
        private int number;
        private boolean current;
    
        public PageItem(int number, boolean current){
            this.number = number;
            this.current = current;
        }

        public int getNumber(){
            return this.number;
        }

        public boolean isCurrent(){
            return this.current;
        }
    }
}
