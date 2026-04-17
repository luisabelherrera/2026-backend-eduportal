package com.example.demo.services.service;

import com.example.demo.model.entity.Noticia;
import com.example.demo.model.entity.dto.NoticiaDTO;
import java.util.List;

public interface NoticiaService {
    Noticia crearNoticia(NoticiaDTO noticiaDTO);
    List<Noticia> obtenerNoticias();
    Noticia obtenerNoticiaPorId(Long id);
    Noticia actualizarNoticia(Long id, NoticiaDTO noticiaDTO);
    void eliminarNoticia(Long id);
    Noticia agregarComentario(Long id, Noticia.Comentario comentario);
    Noticia darLike(Long id);
}
