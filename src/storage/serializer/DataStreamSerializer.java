package storage.serializer;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            dos.writeInt(r.getContacts().size());
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(r.getSections().size());
            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                if (entry.getValue() instanceof TextSection) {
                    writeTextSection(dos, entry);
                }
                if (entry.getValue() instanceof ListSection) {
                    writeListSection(dos, entry);
                }
                if (entry.getValue() instanceof OrganizationSection) {
                    writeOrganizationSection(dos, entry);
                }
            }
        }
    }

    private void writeOrganizationSection(DataOutputStream dos, Map.Entry<SectionType, Section> entry) throws IOException {
        dos.writeUTF("OrganizationSection");
        dos.writeUTF(entry.getKey().name());
        int size = ((OrganizationSection) entry.getValue()).getOrganizations().size();
        dos.writeInt(size);
        for (Organization organization : ((OrganizationSection) entry.getValue()).getOrganizations()) {
            dos.writeUTF(organization.getHomePage().getName());
            dos.writeUTF(organization.getHomePage().getUrl() == null ? "null" : organization.getHomePage().getUrl());
            size = organization.getPositions().size();
            dos.writeInt(size);
            for (Organization.Position position : organization.getPositions()) {
                dos.writeUTF(position.getTitle());
                dos.writeUTF(position.getStartDate().toString());
                dos.writeUTF(position.getEndDate().toString());
                dos.writeUTF(position.getDescription() == null ? "null" : position.getDescription());
            }
        }
    }

    private void writeTextSection(DataOutputStream dos, Map.Entry<SectionType, Section> entry) throws IOException {
        dos.writeUTF("TextSection");
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(((TextSection) entry.getValue()).getContent());
    }

    private void writeListSection(DataOutputStream dos, Map.Entry<SectionType, Section> entry) throws IOException {
        dos.writeUTF("ListSection");
        dos.writeUTF(entry.getKey().name());
        int size = ((ListSection) entry.getValue()).getItems().size();
        dos.writeInt(size);
        for (String str : ((ListSection) entry.getValue()).getItems()) {
            dos.writeUTF(str);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String sectionType = dis.readUTF();
                switch (sectionType) {
                    case "TextSection":
                        readTextSection(dis, resume);
                        break;
                    case "ListSection":
                        readListSection(dis, resume);
                        break;
                    case "OrganizationSection":
                        readOrganizationSection(dis, resume);
                        break;
                }
            }
            return resume;
        }
    }

    private void readOrganizationSection(DataInputStream dis, Resume resume) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        int size = dis.readInt();
        List<Organization> organizations = new ArrayList<Organization>();
        for (int i = 0; i < size; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            if (url.equals("null")) {
                url = null;
            }
            Link homePage = new Link(name, url);
            int sizePosition = dis.readInt();
            List<Organization.Position> positions = new ArrayList<Organization.Position>();
            for (int j = 0; j < sizePosition; j++) {
                String title = dis.readUTF();
                LocalDate startDate = LocalDate.parse(dis.readUTF());
                LocalDate endDate = LocalDate.parse(dis.readUTF());
                String description = dis.readUTF();
                if (description.equals("null")) {
                    description = null;
                }
                positions.add(new Organization.Position(startDate, endDate, title, description));
            }
            organizations.add(new Organization(homePage, positions));
        }
        resume.addSection(sectionType, new OrganizationSection(organizations));
    }

    private void readListSection(DataInputStream dis, Resume resume) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        int size = dis.readInt();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        resume.addSection(sectionType, new ListSection(list));
    }

    private void readTextSection(DataInputStream dis, Resume resume) throws IOException {
        resume.addSection(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
    }


}