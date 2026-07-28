package com.example.NursingHome.service;

import com.example.NursingHome.entity.Residents;
import com.example.NursingHome.repository.ResidentRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
public class ResidentRepositoryImpl implements ResidentRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Residents> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Residents> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Residents> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Residents getOne(Long aLong) {
        return null;
    }

    @Override
    public Residents getById(Long aLong) {
        return null;
    }

    @Override
    public Residents getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Residents> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Residents> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Residents> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Residents> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Residents> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Residents> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Residents, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Residents> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Residents> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Residents> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Residents> findAll() {
        return null;
    }

    @Override
    public List<Residents> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Residents entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Residents> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Residents> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Residents> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Residents> findByRoomNumber(int roomNumber) {
        return Optional.empty();
    }

    @Override
    public Optional<Residents> findFirstByLastNameContainingIgnoreCase(String lastName) {
        return Optional.empty();
    }

    @Override
    public Optional<Residents> findFirstByMiddleNameContainingIgnoreCase(String middleName) {
        return Optional.empty();
    }

    @Override
    public Optional<Residents> findByNationalInsuranceNumber(String niNumber) {
        return Optional.empty();
    }


}
