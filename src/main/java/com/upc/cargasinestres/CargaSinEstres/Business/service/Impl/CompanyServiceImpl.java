package com.upc.cargasinestres.CargaSinEstres.Business.service.Impl;

import com.upc.cargasinestres.CargaSinEstres.Business.Shared.validations.CompanyValidation;
import com.upc.cargasinestres.CargaSinEstres.Business.model.dto.Company.request.CompanyRequestDto;
import com.upc.cargasinestres.CargaSinEstres.Business.model.dto.Company.response.CompanyResponseDto;
import com.upc.cargasinestres.CargaSinEstres.Business.model.entity.Company;
import com.upc.cargasinestres.CargaSinEstres.Business.model.entity.Rating;
import com.upc.cargasinestres.CargaSinEstres.Business.model.entity.Servicio;
import com.upc.cargasinestres.CargaSinEstres.Business.repository.ICompanyRepository;
import com.upc.cargasinestres.CargaSinEstres.Business.repository.IServicioRepository;
import com.upc.cargasinestres.CargaSinEstres.Business.service.ICompanyService;
import com.upc.cargasinestres.CargaSinEstres.Shared.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

/**
 * Implementation of the ICompanyService interface.
 * Handles the business logic for company operations.
 * @author Grupo1
 * @version 1.0*/
@Service
public class CompanyServiceImpl implements ICompanyService {

    private final ICompanyRepository companyRepository;
    private final IServicioRepository servicioRepository;
    private final ModelMapper modelMapper;

    //inyeccion de dependencias
    public CompanyServiceImpl(ICompanyRepository companyRepository, IServicioRepository servicioRepository, ModelMapper modelMapper) {

        this.companyRepository = companyRepository;
        this.servicioRepository = servicioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        var companies = companyRepository.findAll();

        return companies.stream()
                .map(company -> {
                    CompanyResponseDto companyResponseDto = modelMapper.map(company, CompanyResponseDto.class);
                    int averageRating = calculateAverageRating(company);
                    companyResponseDto.setAverageRating(averageRating);
                    return companyResponseDto;
                })
                .toList();
    }


    @Override
    public CompanyResponseDto getCompanyById(Long id) {
        var company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la empresa con id: " + id));

        int averageRating = calculateAverageRating(company);

        CompanyResponseDto companyResponseDto = modelMapper.map(company, CompanyResponseDto.class);
        companyResponseDto.setAverageRating(averageRating);

        return companyResponseDto;
    }

    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto) {
        if (companyRepository.findByNameAndTIC(companyRequestDto.getName(), companyRequestDto.getTIC()).isPresent())
            throw new RuntimeException("Ya existe una empresa con ese nombre y numero de RUC");

        if (companyRepository.findByEmail(companyRequestDto.getEmail()).isPresent())
            throw new RuntimeException("Ya existe una empresa con ese email");

        if (companyRepository.findByPhoneNumber(companyRequestDto.getPhoneNumber()).isPresent())
            throw new RuntimeException("Ya existe una empresa con ese numero de telefono");

        CompanyValidation.ValidateCompany(companyRequestDto);

        List<Servicio> servicios = servicioRepository.findAllById(companyRequestDto.getServicioIds());

        var newCompany = modelMapper.map(companyRequestDto, Company.class);

        newCompany.setServicios(servicios);

        var createdCompany = companyRepository.save(newCompany);
        return modelMapper.map(createdCompany, CompanyResponseDto.class);
    }

    @Override
    public CompanyResponseDto updateCompany(Long id, CompanyRequestDto companyRequestDto){
        var company = companyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No se encontró la empresa con id: "+id));
        // se obtiene el company de la base de datos

        CompanyValidation.ValidateCompany(companyRequestDto);

        modelMapper.map(companyRequestDto, company); // se mapea el companyRequestDto a company y se actualiza el company

        // Obtener los servicios correspondientes a través de los IDs proporcionados
        List<Servicio> servicios = servicioRepository.findAllById(companyRequestDto.getServicioIds());

        // Actualizar los servicios que ofrece la empresa
        company.setServicios(servicios);

        Company updatedCompany = companyRepository.save(company); // se guardan los cambios en la base de datos
        return modelMapper.map(updatedCompany, CompanyResponseDto.class); // se retorna un responseDTO con los datos del company actualizado
    }


    @Override
    public CompanyResponseDto getCompanyForLogin(String email, String password) {

        var company = companyRepository.findByEmailAndPassword(email, password); //se obtiene

        if (company.isEmpty())
            throw new ResourceNotFoundException("No existe una empresa con ese email y password"); // se valida

        return modelMapper.map(company, CompanyResponseDto.class); // se retorna un responseDTO con los datos del company
    }

    public static int calculateAverageRating(Company company) {
        if (company == null || company.getRatings() == null || company.getRatings().isEmpty()) {
            return 0;  // Manejo de casos nulos o vacíos
        }

        List<Rating> ratings = company.getRatings();
        double sumRatings = ratings.stream()
                .mapToInt(Rating::getStars)
                .sum();

        return (int) Math.round(sumRatings / ratings.size());
    }

}
