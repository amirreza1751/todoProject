package com.example.demo.mapper.tag;

import com.example.demo.DTO.tag.TagDTO;
import com.example.demo.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TagMapper {
    public abstract Tag toEnity(TagDTO tagDTO);
    public abstract TagDTO toDto(Tag tag);
    public abstract List<TagDTO> toDTO (Collection<Tag> tags);
}
