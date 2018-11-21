package service;

import dto.*;
import entity.Client;
import entity.Doctor;
import entity.TimeSlot;
import entity.View;

import java.util.TreeSet;

import static constant.URL_CONSTANT.CLIENT_PROFILE;
import static constant.URL_CONSTANT.DOCTOR_PROFILE;

public class ProfileActionService {

    public View resolveView(UserProfileInputDTO inputDTO){
        return resolveProfile(inputDTO);
    }

    private View resolveClientProfile(UserProfileInputDTO inputDTO){
        ClientProfileInputDTO dto = (ClientProfileInputDTO) inputDTO;
        Client client = dto.getClient();

        ClientProfileOutputDTO outputDTO = new ClientProfileOutputDTO();
        outputDTO.setClient(client);

        TreeSet<TimeSlot> slots =
                new TimeSlotDBService().getByIUserId(client.getId(), Client.class);
        //setting entries ( TimeSlot - Doctor )
        for (TimeSlot slot: slots){
            Doctor slotDoctor = new DoctorDBService().getById(slot.getDoctorId());
            outputDTO.addSlotDoctorMapEntry(slot, slotDoctor);
        }
        View view = new View(true);
        view.putSessionAttribute("dto", outputDTO);
        view.setPath(CLIENT_PROFILE);

        return view;
    }

    private View resolveDoctorProfile(UserProfileInputDTO inputDTO){
        DoctorProfileInputDTO dto = (DoctorProfileInputDTO) inputDTO;
        Doctor doctor = dto.getDoctor();

        DoctorProfileOutputDTO outputDTO = new DoctorProfileOutputDTO();
        outputDTO.setDoctor(doctor);

        TreeSet<TimeSlot> slots =
                new TimeSlotDBService().getByIUserId(doctor.getId(), Doctor.class);
        //setting entries ( TimeSlot - Doctor )
        for (TimeSlot slot: slots){
            Doctor slotDoctor = new DoctorDBService().getById(slot.getDoctorId());
            outputDTO.addSlotDoctorMapEntry(slot, slotDoctor);
        }
        View view = new View(true);
        view.putSessionAttribute("dto", outputDTO);
        view.setPath(DOCTOR_PROFILE);

        return view;
    }

    private View resolveProfile(UserProfileInputDTO inputDTO){
        String typeName = inputDTO.getClassName();
        if (typeName.equals(ClientProfileInputDTO.class.getName())){
            return resolveClientProfile(inputDTO);
        }
        if (typeName.equals(DoctorProfileInputDTO.class.getName())){
            return resolveDoctorProfile(inputDTO);
        }
        return null;
    }
}
