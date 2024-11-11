// Ubicación: /src/main/java/com/example/chatapp/service/ChatRoomService.java

package com.example.telexpress.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ChatRoomService {
    private final Map<String, String> userRooms = new HashMap<>();

    // Mapa que contiene los usuarios conectados por sala
    private final Map<String, Set<String>> roomUsers = new HashMap<>();

    // Set para almacenar las salas activas (que ya recibieron mensajes)
    private final Set<String> activeRooms = new HashSet<>();

    // Añadir usuario a una sala o redireccionar si ya existe
    public String createOrJoinRoom(String userName) {
        if (userRooms.containsKey(userName)) {
            return userRooms.get(userName);
        }

        String newRoom = "room_" + userName;
        userRooms.put(userName, newRoom);
        roomUsers.put(newRoom, new HashSet<>());
        return newRoom;
    }

    // Obtener los usuarios en una sala específica
    public Set<String> getUsersInRoom(String room) {
        return roomUsers.getOrDefault(room, new HashSet<>());
    }

    // Añadir un usuario a una sala (verificamos que no haya más de 2 usuarios)
    public boolean addUserToRoom(String room, String user) {
        Set<String> users = roomUsers.get(room);
        if (users != null && users.size() < 2) {
            return users.add(user);
        }
        return false;
    }

    // Obtener la sala de un usuario específico
    public String getRoomForUser(String userName) {
        return userRooms.get(userName);
    }

    public boolean roomExists(String roomName) {
        return activeRooms.contains(roomName);
    }

    // Método para añadir una sala
    public void addRoom(String roomName) {
        activeRooms.add(roomName);
    }

    // Método para añadir al monitor (admin) a una sala
    public void addAdminToRoom(String roomName) {
        if (!roomExists(roomName)) {
            addRoom(roomName); // Si no existe, crear la sala
        }
        System.out.println("Admin añadido a la sala: " + roomName);
        // Aquí podrías añadir lógica adicional si necesitas que el admin tenga algún rol especial en la sala.
    }
    // Método para eliminar una sala (opcional si se quiere gestionar el cierre de salas)
    public void removeRoom(String roomName) {
        activeRooms.remove(roomName);
    }

    // Marcar la sala como activa (cuando se recibe un mensaje)
    public void markRoomAsActive(String room) {
        activeRooms.add(room);
    }

    // Obtener las salas activas
    public Set<String> getActiveRooms() {
        return activeRooms;
    }
}
