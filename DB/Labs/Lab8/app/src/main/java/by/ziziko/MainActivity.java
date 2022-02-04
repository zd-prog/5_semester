package by.ziziko;

import static by.ziziko.FilesHelper.ExistBase;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class MainActivity extends AppCompatActivity {

    Note[] allNotes;
    Note[] dayNotes;
    String fname = "notes.xml";
    File f;
    boolean flag;
    ArrayList categoriesList;

    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    Document document;
    Element rootElement;
    ListAdapter listAdapter;
    List<Note> savedNotes;
    List<Note> savedDateNotes = new ArrayList<>();
    int countAll = 0;
    int countAllDates = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendar = findViewById(R.id.calendar);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button addCategoryButton = findViewById(R.id.addCategory);
        Button deleteCategoryButton = findViewById(R.id.deleteCategory);
        ImageButton changeCategoryButton = findViewById(R.id.changeCategory);
        ListView notesList = findViewById(R.id.notesList);
        EditText editText = findViewById(R.id.editText);
        Button xpath = findViewById(R.id.xpath);

        if (JSONHelper.importFromJSON(this) == null)
        {
            categoriesList = new ArrayList();
        }
        else
            categoriesList = JSONHelper.importFromJSON(this);

        Spinner spinner = findViewById(R.id.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categoriesList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final java.util.Date[] date = new java.util.Date[1];

        xpath.setOnClickListener(v ->
        {
            String DateN = date[0].getYear() + "-" + (date[0].getMonth()+1) + "-" + date[0].getDate();
            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath = factory.newXPath();
            ArrayList<Note> arrayList = new ArrayList<>();
            try {
                XPathExpression xPathExpression = xPath.compile("/Notes/Note");
                NodeList list = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
                for (int i = 0; i < list.getLength(); i++)
                {
                    if (list.item(i).getChildNodes().item(0).getTextContent().equals(spinner.getSelectedItem().toString()) &&
                        list.item(i).getChildNodes().item(1).getTextContent().equals(DateN))
                    {
                        Note note = new Note(list.item(i).getChildNodes().item(2).getTextContent(), list.item(i).getChildNodes().item(0).getTextContent(),
                                Date.valueOf(list.item(i).getChildNodes().item(1).getTextContent()));
                        arrayList.add(note);
                    }
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            Dialog dialogXPath = new Dialog(MainActivity.this);
            dialogXPath.setContentView(R.layout.dialog_xpath);
            ListView listView = dialogXPath.findViewById(R.id.list);
            ArrayAdapter<Note> noteArrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, arrayList);
            listView.setAdapter(noteArrayAdapter);
            dialogXPath.show();
        });



        f = new File(super.getFilesDir(), fname);
        f.setReadable(true);
        allNotes = new Note[20];
        boolean r = f.canRead();
        try {
            if (!ExistBase(f))
            {
                f.createNewFile();
                builder = builderFactory.newDocumentBuilder();
                document = builder.newDocument();

                rootElement = document.createElement("Notes");
                document.appendChild(rootElement);
                Element note = document.createElement("Note");
                Element category = document.createElement("Category");
                category.setTextContent("Study");
                Element Date = document.createElement("Date");
                Date.setTextContent("2021-10-22");
                Element text = document.createElement("Text");
                text.setTextContent("Do 8 lab");
                note.appendChild(category);
                note.appendChild(Date);
                note.appendChild(text);
                rootElement.appendChild(note);
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                DOMSource source = new DOMSource(document);

                FileOutputStream outputStream = openFileOutput(fname, MODE_PRIVATE);
                StreamResult result = new StreamResult(outputStream);
                transformer.transform(source, result);
                outputStream.close();
            }
            else
            {
                builder = builderFactory.newDocumentBuilder();
                document = builder.parse(f);

                XPathFactory factory = XPathFactory.newInstance();
                XPath xPath = factory.newXPath();

                XPathExpression expression = xPath.compile("/Notes/Note");
                NodeList list = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

                savedNotes = new ArrayList<>();
                for (int i = 0; i < list.getLength(); i++)
                {
                    NodeList children = list.item(i).getChildNodes();

                    Note note = new Note(children.item(2).getTextContent(), children.item(0).getTextContent(),
                            Date.valueOf(children.item(1).getTextContent()));


                    if (countAll < 20)
                    {
                        savedNotes.add(note);
                        countAll++;
                    }
                }


            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }




        buttonAdd.setOnClickListener(v ->
        {
            if (!editText.equals("") && !spinner.getSelectedItem().toString().equals(""))
            {
                try {
                    String DateN = date[0].getYear() + "-" + (date[0].getMonth()+1) + "-" + date[0].getDate();
                    Note note = new Note(editText.getText().toString(), spinner.getSelectedItem().toString(), Date.valueOf(DateN));
                    savedNotes.add(note);
                    XmlDoc(savedNotes);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }
            editText.setText("");

        });


        calendar.setOnDateChangeListener((calendarView, i, i1, i2) ->
        {
            savedDateNotes = new ArrayList<>();
            date[0] = new java.util.Date(i, i1, i2);

            if (allNotes!=null)
            {
                for (Note n : allNotes)
                {
                    if (n == null) {
                        flag = true;
                        break;
                    }
                }

                if (!flag)
                {
                    buttonAdd.setVisibility(View.INVISIBLE);
                }

            }

            for (Note n: savedNotes
            )
            {
                String Date = date[0].getYear() + "-" + (date[0].getMonth()+1) + "-" + date[0].getDate();
                if (n.date.toString().equals(Date) && countAllDates < 5)
                {
                    savedDateNotes.add(n);
                    countAllDates++;
                }
            }
            listAdapter = new ArrayAdapter<Note>(this, R.layout.list_item, savedDateNotes);
            notesList.setAdapter(listAdapter);
            notesList.setClickable(true);
            notesList.setOnItemClickListener((adapterView, view, i3, l) ->
            {
                Dialog dialogItem = new Dialog(MainActivity.this);
                dialogItem.setContentView(R.layout.dialog_item);
                EditText textNote = dialogItem.findViewById(R.id.textNote);
                Button change = dialogItem.findViewById(R.id.changeButton);
                Button delete = dialogItem.findViewById(R.id.delButton);
                textNote.setText(savedDateNotes.get(i3).text);
                dialogItem.show();

                change.setOnClickListener(v ->
                {
                    Note note = new Note();
                    note.date = savedDateNotes.get(i3).date;
                    note.category = savedDateNotes.get(i3).category;
                    savedNotes.remove(savedDateNotes.get(i3));
                    savedDateNotes.remove(i3);
                    note.text = textNote.getText().toString();
                    savedNotes.add(note);
                    try {
                        XmlDoc(savedNotes);
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialogItem.cancel();
                });

                delete.setOnClickListener(v ->
                {
                    savedNotes.remove(savedDateNotes.get(i3));
                    savedDateNotes.remove(i3);
                    dialogItem.cancel();
                });

            });



        });

        addCategoryButton.setOnClickListener(v ->
        {
            if (categoriesList.size() < 5)
            {
                Dialog addCategory = new Dialog(MainActivity.this);
                addCategory.setContentView(R.layout.dialog);
                EditText categoryNew = addCategory.findViewById(R.id.categoryName);
                Button add = addCategory.findViewById(R.id.addButton);
                addCategory.show();
                add.setOnClickListener(q ->
                {
                    if (categoryNew.getText().toString().equals(""))
                    {
                        Toast toast = Toast.makeText(this, "Введите название категории", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else
                    {
                        categoriesList.add(categoryNew.getText().toString());
                        JSONHelper.exportToJSON(this, categoriesList);
                        addCategory.cancel();
                    }


                });
            }
            else
            {
                Toast toast = Toast.makeText(this, "Максимальное количество категорий", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        deleteCategoryButton.setOnClickListener(v ->
        {
            int delCategoryNum = categoriesList.indexOf(spinner.getSelectedItem());
            categoriesList.remove(delCategoryNum);
            spinner.setSelection(0);
            JSONHelper.exportToJSON(this, categoriesList);
        });

        changeCategoryButton.setOnClickListener(v ->
        {
            Dialog editCategory = new Dialog(MainActivity.this);
            editCategory.setContentView(R.layout.dialog);
            EditText categoryNew = editCategory.findViewById(R.id.categoryName);
            categoryNew.setText(spinner.getSelectedItem().toString());
            Button add = editCategory.findViewById(R.id.addButton);
            editCategory.show();
            int num = categoriesList.indexOf(categoryNew.getText().toString());
            add.setOnClickListener(q ->
            {
                categoriesList.set(num, (categoryNew.getText().toString()));
                JSONHelper.exportToJSON(this, categoriesList);
                editCategory.cancel();
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void XmlDoc(List<Note> notes) throws ParserConfigurationException, TransformerException, IOException {
        builder = builderFactory.newDocumentBuilder();
        document = builder.newDocument();

        rootElement = document.createElement("Notes");
        document.appendChild(rootElement);


        for (Note n: notes)
        {
            Element note = document.createElement("Note");
            Element category = document.createElement("Category");
            Element Date = document.createElement("Date");
            Element text = document.createElement("Text");
            category.setTextContent(n.category);
            Date.setTextContent(n.date.toString());
            text.setTextContent(n.text);
            note.appendChild(category);
            note.appendChild(Date);
            note.appendChild(text);
            rootElement.appendChild(note);
        }


        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource source = new DOMSource(document);

        FileOutputStream outputStream = openFileOutput(fname, MODE_PRIVATE);
        StreamResult result = new StreamResult(outputStream);
        transformer.transform(source, result);
        outputStream.close();
    }
}