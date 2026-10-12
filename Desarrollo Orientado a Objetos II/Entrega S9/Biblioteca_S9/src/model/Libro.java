package model;
import interfaces.Identificable;
public final class Libro implements Identificable {
 private final int id, categoriaId, stock, stockTotal;
 private final String titulo,autor,editorial,isbn,categoria;
 public Libro(int id,String titulo,String autor,String editorial,String isbn,int categoriaId,String categoria,int stock,int stockTotal) {
  this.id=id;this.titulo=titulo;this.autor=autor;this.editorial=editorial;this.isbn=isbn;this.categoriaId=categoriaId;this.categoria=categoria;this.stock=stock;this.stockTotal=stockTotal;
 }
 public int getId(){return id;} public String getTitulo(){return titulo;} public String getAutor(){return autor;}
 public String getEditorial(){return editorial;} public String getIsbn(){return isbn;} public int getCategoriaId(){return categoriaId;}
 public String getCategoria(){return categoria;} public int getStock(){return stock;} public int getStockTotal(){return stockTotal;}
 public String toString(){return titulo+" | disponibles: "+stock;}
}
