package goldstar_backend.service;

import goldstar_backend.dto.MeasurementRequest;
import goldstar_backend.dto.MeasurementResponse;
import goldstar_backend.entity.Customer;
import goldstar_backend.entity.Measurement;
import goldstar_backend.repository.CustomerRepository;
import goldstar_backend.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final CustomerRepository customerRepository;

    public MeasurementResponse saveMeasurement(Long customerId,
                                               MeasurementRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Measurement measurement = Measurement.builder()
                .height(request.getHeight())
                .weight(request.getWeight())
                .neck(request.getNeck())
                .shoulder(request.getShoulder())
                .chest(request.getChest())
                .waist(request.getWaist())
                .hip(request.getHip())
                .sleeveLength(request.getSleeveLength())
                .armLength(request.getArmLength())
                .wrist(request.getWrist())
                .thigh(request.getThigh())
                .calf(request.getCalf())
                .inseam(request.getInseam())
                .outseam(request.getOutseam())
                .ankle(request.getAnkle())
                .bodyType(request.getBodyType())
                .gender(request.getGender())
                .customer(customer)
                .build();

        Measurement saved = measurementRepository.save(measurement);

        return MeasurementResponse.builder()
                .id(saved.getId())
                .height(saved.getHeight())
                .weight(saved.getWeight())
                .neck(saved.getNeck())
                .shoulder(saved.getShoulder())
                .chest(saved.getChest())
                .waist(saved.getWaist())
                .hip(saved.getHip())
                .sleeveLength(saved.getSleeveLength())
                .armLength(saved.getArmLength())
                .wrist(saved.getWrist())
                .thigh(saved.getThigh())
                .calf(saved.getCalf())
                .inseam(saved.getInseam())
                .outseam(saved.getOutseam())
                .ankle(saved.getAnkle())
                .bodyType(saved.getBodyType())
                .gender(saved.getGender())
                .customerId(customer.getId())
                .build();
    }

    public MeasurementResponse getMeasurement(Long customerId) {

        Measurement measurement = measurementRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Measurement not found"));

        return MeasurementResponse.builder()
                .id(measurement.getId())
                .height(measurement.getHeight())
                .weight(measurement.getWeight())
                .neck(measurement.getNeck())
                .shoulder(measurement.getShoulder())
                .chest(measurement.getChest())
                .waist(measurement.getWaist())
                .hip(measurement.getHip())
                .sleeveLength(measurement.getSleeveLength())
                .armLength(measurement.getArmLength())
                .wrist(measurement.getWrist())
                .thigh(measurement.getThigh())
                .calf(measurement.getCalf())
                .inseam(measurement.getInseam())
                .outseam(measurement.getOutseam())
                .ankle(measurement.getAnkle())
                .bodyType(measurement.getBodyType())
                .gender(measurement.getGender())
                .customerId(customerId)
                .build();
    }

    public MeasurementResponse updateMeasurement(Long customerId,
                                                 MeasurementRequest request) {

        Measurement measurement = measurementRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Measurement not found"));

        measurement.setHeight(request.getHeight());
        measurement.setWeight(request.getWeight());
        measurement.setNeck(request.getNeck());
        measurement.setShoulder(request.getShoulder());
        measurement.setChest(request.getChest());
        measurement.setWaist(request.getWaist());
        measurement.setHip(request.getHip());
        measurement.setSleeveLength(request.getSleeveLength());
        measurement.setArmLength(request.getArmLength());
        measurement.setWrist(request.getWrist());
        measurement.setThigh(request.getThigh());
        measurement.setCalf(request.getCalf());
        measurement.setInseam(request.getInseam());
        measurement.setOutseam(request.getOutseam());
        measurement.setAnkle(request.getAnkle());
        measurement.setBodyType(request.getBodyType());
        measurement.setGender(request.getGender());

        Measurement updated = measurementRepository.save(measurement);

        return MeasurementResponse.builder()
                .id(updated.getId())
                .height(updated.getHeight())
                .weight(updated.getWeight())
                .neck(updated.getNeck())
                .shoulder(updated.getShoulder())
                .chest(updated.getChest())
                .waist(updated.getWaist())
                .hip(updated.getHip())
                .sleeveLength(updated.getSleeveLength())
                .armLength(updated.getArmLength())
                .wrist(updated.getWrist())
                .thigh(updated.getThigh())
                .calf(updated.getCalf())
                .inseam(updated.getInseam())
                .outseam(updated.getOutseam())
                .ankle(updated.getAnkle())
                .bodyType(updated.getBodyType())
                .gender(updated.getGender())
                .customerId(customerId)
                .build();
    }
}