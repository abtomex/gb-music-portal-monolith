package ru.geekbrains.musicportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.geekbrains.musicportal.entity.user.MusicGroup;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupDto extends AbstractDto {

    private Collection<UserDto> participants;

    public void GroupDto(MusicGroup group) {
        super.setId(group.getId());
        super.setName(group.getName());
        super.setDescription(group.getDescription());
        if (participants != null) {
            participants.clear();
        } else {
            participants = new ArrayList<>();
        }

    }
}