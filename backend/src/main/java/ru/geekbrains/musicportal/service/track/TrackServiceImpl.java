package ru.geekbrains.musicportal.service.track;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.musicportal.dto.track.TrackDto;
import ru.geekbrains.musicportal.entity.track.Track;
import ru.geekbrains.musicportal.enums.FilterJoinTypeEnum;
import ru.geekbrains.musicportal.pojo.SpecFeature;
import ru.geekbrains.musicportal.repository.LikeRepository;
import ru.geekbrains.musicportal.repository.TrackRepository;
import ru.geekbrains.musicportal.util.TrackUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackServiceImpl implements TrackService {

    private TrackRepository trackRepository;
    private LikeRepository likeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository,
                            LikeRepository likeRepository,
                            ModelMapper modelMapper) {
        this.trackRepository = trackRepository;
        this.likeRepository = likeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Track save(Track entity) {
        return trackRepository.save(entity);
    }

    @Override
    public Optional<Track> findOneEntityById(Long id) {
        return trackRepository.findById(id);
    }

    public Page<Track> getTrackWithPagingAndFiltering(int pageNumber, int pageSize, List<SpecFeature> specFeatures) {
        Specification<Track> trackSpecification = Specification.where(null);
        for (SpecFeature specFeature : specFeatures) {
            if (specFeature.getJoinType() == FilterJoinTypeEnum.AND) {
                trackSpecification = trackSpecification.and(TrackUtil.getSpecification(specFeature));
            } else if (specFeature.getJoinType() == FilterJoinTypeEnum.OR) {
                trackSpecification = trackSpecification.or(TrackUtil.getSpecification(specFeature));
            } else {
                trackSpecification = trackSpecification.and(TrackUtil.getSpecification(specFeature));
            }
        }
        return trackRepository.findAll(trackSpecification, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public void deleteById(Long id) {
        trackRepository.deleteById(id);
    }

    @Override
    public Track convertToEntity(TrackDto dto) {
        return modelMapper.map(dto, Track.class);
    }

    /**
     * Функция выдает Топ треков
     * @param topMax Максимальное количество треков в рейтинге
     * @return Коллекция объектов TrackDto
     */
    public Collection<TrackDto> getTopTracks(int topMax){
        return likeRepository.getTopTracks("TRACK", PageRequest.of(0, topMax))
                .stream()
                .sorted(Comparator.comparingLong(o -> (Long) o[1]))
                .map(objects -> {
                    Track track = trackRepository.findById((Long)objects[0]).orElse(null);
                    if (track == null) return null;
                    return new TrackDto(track, (Long)objects[1]);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Track findTrackById(Long id) {
        return trackRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<TrackDto> findAllDto() {
        return trackRepository.findAllByIdNotNull();
    }

    @Override
    public TrackDto findOneDtoById(Long id) {
        return trackRepository.findOneById(id);
    }
}
