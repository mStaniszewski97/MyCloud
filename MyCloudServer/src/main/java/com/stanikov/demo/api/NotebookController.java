package com.stanikov.demo.api;

//@RestController
//@RequestMapping("/api/notebooks")
//@CrossOrigin
public class NotebookController {
//    private NotebookRepository notebookRepository;
//    private Mapper mapper;
//
//    public NotebookController(NotebookRepository notebookRepository, Mapper mapper) {
//        this.notebookRepository = notebookRepository;
//        this.mapper = mapper;
//    }
//
//    @GetMapping("/all")
//    public List<Notebook> all() {
//        List<Notebook> allCategories = this.notebookRepository.findAll();
//        return allCategories;
//    }
//
//    @PostMapping
//    public Notebook save(@RequestBody NotebookViewModel notebookViewModel,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            throw new ValidationException();
//        }
//
//        Notebook notebookEntity = this.mapper.convertToNotebookEntity(notebookViewModel);
//
//        // save notebookEntity instance to db
//        this.notebookRepository.save(notebookEntity);
//
//        return notebookEntity;
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable String id) {
//        this.notebookRepository.deleteById(UUID.fromString(id));
//    }
}
