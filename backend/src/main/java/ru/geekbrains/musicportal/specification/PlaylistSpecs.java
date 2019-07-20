package ru.geekbrains.musicportal.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.musicportal.entity.playlist.Playlist;

public class PlaylistSpecs {

    public static Specification<Playlist> playlistNameContains(String word) {
        return (Specification<Playlist>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + word + "%");
    }
}
