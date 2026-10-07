package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public ClientDto getById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return convertToDto(client);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public ClientDto create(ClientDto clientDto) {
        Client client = convertToEntity(clientDto);
        return convertToDto(clientRepository.save(client));
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public ClientDto update(ClientDto clientDto) {
        Client client = clientRepository.findById(clientDto.getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        client.setName(clientDto.getName());
        client.setDni(clientDto.getDni());
        client.setGender(clientDto.getGender());
        client.setAge(clientDto.getAge());
        client.setAddress(clientDto.getAddress());
        client.setPhone(clientDto.getPhone());
        client.setPassword(clientDto.getPassword());
        client.setActive(clientDto.isActive());

        return convertToDto(clientRepository.save(client));
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        client.setActive(partialClientDto.isActive());

        return convertToDto(clientRepository.save(client));
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clientRepository.deleteById(id);
    }

    // Mapeo
    private ClientDto convertToDto(Client client) {
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setDni(client.getDni());
        dto.setGender(client.getGender());
        dto.setAge(client.getAge());
        dto.setAddress(client.getAddress());
        dto.setPhone(client.getPhone());
        dto.setPassword(client.getPassword());
        dto.setActive(client.isActive());
        return dto;
    }

    private Client convertToEntity(ClientDto dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setDni(dto.getDni());
        client.setGender(dto.getGender());
        client.setAge(dto.getAge());
        client.setAddress(dto.getAddress());
        client.setPhone(dto.getPhone());
        client.setPassword(dto.getPassword());
        client.setActive(dto.isActive());
        return client;
    }
}