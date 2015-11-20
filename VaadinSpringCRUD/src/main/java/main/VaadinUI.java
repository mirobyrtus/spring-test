package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	private final GuidelineRepository repo;
	private final GuidelineEditor editor;

//	Navigator navigator;
//    protected static final String MAINVIEW = "main";
	
	private final Grid grid;
	private final TextField filter;
	private final Button addNewBtn;

	@Autowired
	public VaadinUI(GuidelineRepository repo, GuidelineEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("New guideline", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		
		// build layout
		MenuBar menuBar = new MenuBar();
		MenuItem submenu1 = menuBar.addItem("RootDocuments", null);
		MenuItem submenu2 = menuBar.addItem("Guidelines", null);
		MenuItem submenu3 = menuBar.addItem("Evidences", null);
		
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(menuBar, actions, grid, editor);
		setContent(mainLayout);
		
		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "description"); // , "rootDocument"

		filter.setInputPrompt("Filter by description");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listGuidelines(e.getText()));

		// Connect selected RootDocument to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			} else {
				editor.editGuideline((Guideline) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new RootDocument the new button is clicked
		addNewBtn.addClickListener(e -> editor.editGuideline(new Guideline("", new RootDocument(""))));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listGuidelines(filter.getValue());
		});

		// Initialize listing
		listGuidelines(null);
	}

	// tag::listGuidelines[]
	private void listGuidelines(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setContainerDataSource(new BeanItemContainer(Guideline.class, repo.findAll()));
		}
		else {
			grid.setContainerDataSource(new BeanItemContainer(Guideline.class, repo.findByDescriptionStartsWithIgnoreCase(text)));
		}
	}
	// end::listGuidelines[]	
}
