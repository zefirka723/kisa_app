package mosecom.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

// TODO: сделать универсальный класс для вложений?

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Files", schema = "public")
public class Attachment implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Type")
    protected DocumentType documentType;

    @Column(name = "File_Path")
    private String filePath;

    @Column(name = "File_Size")
    private Long fileSize;

    @Column(name = "File_Name")
    private String fileName;

    @Column(name = "File_Content_Type")
    private String fileContentType;

    @Column(name = "Doc_ID")
    private int docId;

    @ManyToOne
    @JoinColumn(name = "Well_ID", referencedColumnName = "Well_ID")
    protected Well well;

    @Column(name="File_Set_ID")
    private int fileSetId;
}
