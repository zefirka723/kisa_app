package mosecom.model.inspections;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Files", schema = "public")
public class File implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "File_Path")
    private String filePath;

    @Column(name = "File_Size")
    private Long fileSize;

    @Column(name = "File_Name")
    private String fileName;

    @Column(name = "File_Content_Type")
    private String fileContentType;

    @Column(name="File_Set_ID")
    private int fileSetId;

//    @Column(name = "Doc_ID")
//    private int docId;

//    @ManyToOne
//    @JoinColumn(name = "Type")
//    protected DocumentType documentType;

}
